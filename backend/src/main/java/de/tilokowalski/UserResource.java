package de.tilokowalski;

import com.surrealdb.connection.exception.SurrealException;
import com.surrealdb.connection.exception.SurrealRecordAlreadyExitsException;
import de.tilokowalski.db.Surreal;
import de.tilokowalski.model.User;
import de.tilokowalski.utils.SpotifyUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import lombok.extern.java.Log;
import org.jboss.resteasy.reactive.RestHeader;

@Path("/user")
@Log
public class UserResource {
  @Inject
  Surreal db;
  @GET
  public User getUserInformation(@RestHeader("Authorization") String accessToken) {
    SpotifyUtil spotify = new SpotifyUtil(accessToken);
    try {
      // TODO: use mapping for spotify user to our user
      db.store(new User("1,","test"));
    } catch (SurrealException e) {
      if (!(e instanceof SurrealRecordAlreadyExitsException)) {
        throw e;
      }
      log.warning("user with id 1 already exists");
    }
    return spotify.getUser();
  }
}
