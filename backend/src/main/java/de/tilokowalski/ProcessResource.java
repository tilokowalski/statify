package de.tilokowalski;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.surrealdb.connection.exception.SurrealException;
import com.surrealdb.connection.exception.SurrealRecordAlreadyExitsException;
import de.tilokowalski.db.Surreal;
import de.tilokowalski.model.Artist;
import de.tilokowalski.model.Listens;
import de.tilokowalski.model.Track;
import de.tilokowalski.model.User;
import de.tilokowalski.util.ToString;
import de.tilokowalski.utils.SpotifyUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.extern.java.Log;
import org.jboss.resteasy.reactive.RestHeader;

import java.time.Duration;
import java.util.List;

@Path("/process")
@Log
public class ProcessResource {

    @Inject
    Surreal db;

    @POST
    @Path("{userId}")
    public void process(@RestHeader("Authorization") String accessToken, String userId) {
        // fetch data from spotify (user id and tracks)
        // store mapped data into database
        User currentUser = new User(userId);
        try {
            List<User> users = db.get(currentUser, User.class);
            if (users.isEmpty()) {
                db.store(currentUser);
            } else {
                return;
            }
        } catch (SurrealException e) {
            if (!(e instanceof SurrealRecordAlreadyExitsException)) {
                throw e;
            }
            log.info("Record for user already exists:" + ToString.createDump(currentUser));
        }


        SpotifyUtil spotify = new SpotifyUtil(accessToken);
        List<Listens> trackListens = spotify.getPlayHistoryListens(currentUser, Duration.ofDays(5));

        for (Listens listen : trackListens) {
            Track track = listen.out();
            try {
                db.relate(listen);
            } catch (JsonProcessingException e) {
                throw new InternalServerErrorException(e.getMessage());
            } catch (SurrealException e) {
                log.warning("error relating listens: " + ToString.create(e));
                throw new InternalServerErrorException(e);
            }

            try {
                db.store(track);
            } catch (SurrealException e) {
                if (!(e instanceof SurrealRecordAlreadyExitsException)) {
                    throw new InternalServerErrorException(e.getMessage());
                }
                log.info("Record for track already exists: " + ToString.createDump(track));
            }

            for (Artist artist : track.getArtists()) {
                try {
                    db.store(artist);
                } catch (SurrealException e) {
                    if (!(e instanceof SurrealRecordAlreadyExitsException)) {
                        throw new InternalServerErrorException(e.getMessage());
                    }
                    log.info("Record for artist already exists: " + ToString.createDump(artist));
                }
            }
        }
    }
}
