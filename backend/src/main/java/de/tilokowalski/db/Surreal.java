package de.tilokowalski.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.surrealdb.connection.SurrealConnection;
import com.surrealdb.connection.SurrealWebSocketConnection;
import com.surrealdb.connection.exception.SurrealException;
import com.surrealdb.driver.SyncSurrealDriver;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.java.Log;

/**
 * The surreal graph database connection, wrapped into a singleton object.
 */
import java.util.List;

@Singleton
@Log
public class Surreal {

    /**
     * The surreal driver is used to communicate with the surreal graph database.
     */
    private final SyncSurrealDriver driver;

    /**
     * The object mapper is used to serialize relations.
     */
    @Inject
    ObjectMapper mapper;

    /**
     * Creates the surreal graph database connection.
     */
    private Surreal() {
        SurrealConnection connection = new SurrealWebSocketConnection("127.0.0.1", 8000, false);
        connection.connect(30); // timeout after 30 seconds

        driver = new SyncSurrealDriver(connection);

        driver.signIn("root", "root"); // username & password
        driver.use("statify", "statify"); // namespace & database
    }

    /**
     * Stores a record in the surreal graph database.
     *
     * @param thing The record to store.
     */
    public Thing store(Thing thing) {
        return driver.create(thing.toString(), thing);
    }

    public <T extends Thing> List<T> get(String tableName, Class<T> recordType) {
        return driver.select(tableName, recordType);
    }


    public <T extends Thing> List<T> get(String tableName, String recordId, Class<T> recordType) {
        return driver.select(tableName + ":" + recordId, recordType);
    }

    public <T extends Thing> List<T> get(T record, Class<T> recordType) {
        return driver.select(record.toString(), recordType);
    }


    public <T extends Thing> void update(T record) {
        driver.update(record.toString(), record);
    }
    /**
     * Relates two records in the surreal graph database.
     *
     * @param relation The relation to store.
     * @throws SurrealException If the relation could not be serialized.
     */
    public void relate(Relation<?, ?> relation) throws SurrealException {
        try {
            String contentJson = mapper.writeValueAsString(relation);

            var query = String.format(
                    "RELATE %s->%s->%s CONTENT %s",
                    relation.in().toString(),
                    relation.relationName(),
                    relation.out().toString(),
                    contentJson
            );

            driver.query(query, null, relation.getClass());

        } catch (JsonProcessingException e) {
            throw new SurrealException("unable to serialize relation into json: " + e.getMessage());
        }
    }

}
