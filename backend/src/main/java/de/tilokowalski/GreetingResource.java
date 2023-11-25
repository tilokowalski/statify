package de.tilokowalski;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.tilokowalski.db.Surreal;
import de.tilokowalski.model.Listens;
import de.tilokowalski.model.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDateTime;

@Path("/hello")
public class GreetingResource {

  @Inject
  Surreal db;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {

    //db.store("person", "herbert", User.builder().name("herbert").build());
    return "Hello from RESTEasy Reactive";
  }
}
