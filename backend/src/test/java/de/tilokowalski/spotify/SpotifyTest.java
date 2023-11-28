package de.tilokowalski.spotify;

import de.tilokowalski.db.Surreal;
import de.tilokowalski.model.User;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class SpotifyTest {

    private static final String ACCESS_TOKEN = "BQCRx7MpmXsYeNS_IxdAJy5gRccvk2ecLk3foEWd3jKh2e1xxc8egm-djoT9znTsrSHZ4BmIEmohiaGHUxq38O0pMvkBVPn8UTmLUdk4-uolsrtQxOURBFYNM1xZcpZiRTEKs4pOc2t0E-CR_v3V01Yvyfp3N8fhB9G_wrS4U7j3-k8p2qnICCu-fcLrByAwg_pE-52HfiJGwlACQSwRZue3";

    @InjectMock
    Surreal db;
    private static Spotify spotify;

    public void init() {
        spotify = new Spotify(db, ACCESS_TOKEN);
    }

    @Test
    // @Disabled
    public void getPlayHistoryListens30Days() {
        init();
        spotify.getPlayHistoryAndStore(new User("1"));
    }
}
