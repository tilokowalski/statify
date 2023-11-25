package de.tilokowalski;

import de.tilokowalski.db.Surreal;
import de.tilokowalski.model.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

  @Inject
  private Surreal db;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {

    User user = new User();
    db.getDriver().create("user", user);
    return "Hello from RESTEasy Reactive";
  }
}
