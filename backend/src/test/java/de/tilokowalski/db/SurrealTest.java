package de.tilokowalski.db;

import de.tilokowalski.model.Listens;
import de.tilokowalski.model.Track;
import de.tilokowalski.model.User;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

@QuarkusTest
class SurrealTest {

    @Inject
    Surreal db;

    @Test
    @SneakyThrows
    void relate() {
        var user = new User("1");
        var track = new Track("1", "1", Collections.emptyList());
        var listens = new Listens(user, track, LocalDateTime.now());

        db.relate(listens);
    }

    @Test
    void store() {
        var user = new User("herbert");
        db.store(user);
    }

    @Test
    void get() {
        var user = new User("detlef");
        List<User> _users = db.get(user.getTableName(), User.class);
    }
}