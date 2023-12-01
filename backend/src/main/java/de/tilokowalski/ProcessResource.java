package de.tilokowalski;

import com.surrealdb.connection.exception.SurrealException;
import com.surrealdb.connection.exception.SurrealRecordAlreadyExitsException;
import de.tilokowalski.db.Surreal;
import de.tilokowalski.model.User;
import de.tilokowalski.spotify.Spotify;
import de.tilokowalski.util.ToString;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.extern.java.Log;
import org.jboss.resteasy.reactive.RestHeader;

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
        Spotify spotify = new Spotify(db, accessToken);

        try {
            List<User> users = db.get(currentUser, User.class);
            if (users.isEmpty()) {
                db.store(currentUser);
            }
        } catch (SurrealException e) {
            if (!(e instanceof SurrealRecordAlreadyExitsException)) {
                throw e;
            }
            log.info("Record for user already exists:" + ToString.createDump(currentUser));
        }
        spotify.getPlayHistoryAndStore(currentUser);
    }
}
