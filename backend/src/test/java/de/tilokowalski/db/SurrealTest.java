package de.tilokowalski.db;

import de.tilokowalski.model.Listens;
import de.tilokowalski.model.Track;
import de.tilokowalski.model.User;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class SurrealTest {

    @Inject
    Surreal db;

    @Test
    @SneakyThrows
    void relate() {
        var user = new User("1","herbert");
        var track = new Track("1", "1", Collections.emptyList());
        var listens = new Listens(user, track, LocalDateTime.now());

        db.relate(listens);
    }

    @Test
    void store() {
        var user = new User("herbert");
        db.store(user);
    }
}