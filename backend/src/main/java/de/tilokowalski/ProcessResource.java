package de.tilokowalski;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.surrealdb.connection.exception.SurrealException;
import com.surrealdb.connection.exception.SurrealRecordAlreadyExitsException;
import de.tilokowalski.db.Surreal;
import de.tilokowalski.model.Artist;
import de.tilokowalski.model.Listens;
import de.tilokowalski.model.Track;
import de.tilokowalski.model.User;
import de.tilokowalski.utils.SpotifyUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.extern.java.Log;
import org.jboss.resteasy.reactive.RestHeader;

import java.time.LocalDateTime;
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
            List<User> users = db.get(new User().getTableName(), User.class);
            if (users.isEmpty()) {
                db.store(currentUser);
            }
        } catch (SurrealException e) {
            if (e instanceof SurrealRecordAlreadyExitsException) {
                log.info("Record for user already exists:" + e.getCause());
            }
            throw e;
        }


        SpotifyUtil spotify = new SpotifyUtil(accessToken);
        List<Listens> trackListens = spotify.getPlayHistoryListens30Days(currentUser);

        for (Listens listen : trackListens) {
            Track track = listen.out();
            try {
                db.store(track);

                db.relate(listen);
            } catch (SurrealException e) {
                if (e instanceof SurrealRecordAlreadyExitsException) {
                    log.info("Record for track already exists:" + e.getCause());
                }
                throw new InternalServerErrorException(e.getMessage());
            } catch (JsonProcessingException e) {
                throw new InternalServerErrorException(e.getMessage());
            }

            for (Artist artist: track.getArtists()) {
                try {
                    db.store(artist);
                } catch (SurrealException e) {
                    if (e instanceof SurrealRecordAlreadyExitsException) {
                        log.info("Record for track already exists:" + e.getCause());
                    }
                    throw new InternalServerErrorException(e.getMessage());
                }
            }
        }
    }
}
