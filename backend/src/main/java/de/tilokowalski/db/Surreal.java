package de.tilokowalski.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.surrealdb.connection.SurrealConnection;
import com.surrealdb.connection.SurrealWebSocketConnection;
import com.surrealdb.driver.SyncSurrealDriver;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.java.Log;

@Singleton
@Log
public class Surreal {

  private final SyncSurrealDriver driver;

  @Inject
  ObjectMapper mapper;

  public Surreal() {
    SurrealConnection connection = new SurrealWebSocketConnection("127.0.0.1", 8000, false);
    connection.connect(30); // timeout after 30 seconds

    driver = new SyncSurrealDriver(connection);

    driver.signIn("root", "root"); // username & password
    driver.use("statify", "statify"); // namespace & database
  }

  public void store(
      Record record) {
    driver.create(record.recordId(), record);
  }

  public void relate(Relation relation)
      throws JsonProcessingException {
    String contentJson = mapper.writeValueAsString(relation);
    var query = String.format("RELATE %s->%s->%s CONTENT %s", relation.in().recordId(), relation.relationName(), relation.out().recordId(), contentJson);
    log.info(query);
    driver.query(query, null, relation.getClass());
  }
}
