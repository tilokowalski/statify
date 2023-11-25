package de.tilokowalski.db;

import com.surrealdb.connection.SurrealConnection;
import com.surrealdb.connection.SurrealWebSocketConnection;
import com.surrealdb.driver.SyncSurrealDriver;

import jakarta.inject.Singleton;

@Singleton
public class Surreal {

  private SyncSurrealDriver driver;

  public SyncSurrealDriver getDriver() {
    return driver;
  }

  public Surreal() {
    SurrealConnection connection = new SurrealWebSocketConnection("127.0.0.1", 8000, false);
    connection.connect(30); // timeout after 30 seconds

    driver = new SyncSurrealDriver(connection);

    driver.signIn("root", "root"); // username & password
    driver.use("statify", "statify"); // namespace & database
  }
}
