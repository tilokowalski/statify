package de.tilokowalski;

import de.tilokowalski.db.Surreal;
import de.tilokowalski.utils.SpotifyUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestHeader;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/process")
public class ProcessResource {

  @Inject
  Surreal db;

  @POST
  @Path("{userId}")
  public String process(@RestHeader("Authorization") String accessToken, String userId) {
    // fetch data from spotify (user id and tracks)
    // store mapped data into database
    return "";
  }
}
