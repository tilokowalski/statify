package de.tilokowalski.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import de.tilokowalski.model.Listens;
import de.tilokowalski.model.User;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class SpotifyUtilTest {

    private static final String ACCESS_TOKEN = "BQCRx7MpmXsYeNS_IxdAJy5gRccvk2ecLk3foEWd3jKh2e1xxc8egm-djoT9znTsrSHZ4BmIEmohiaGHUxq38O0pMvkBVPn8UTmLUdk4-uolsrtQxOURBFYNM1xZcpZiRTEKs4pOc2t0E-CR_v3V01Yvyfp3N8fhB9G_wrS4U7j3-k8p2qnICCu-fcLrByAwg_pE-52HfiJGwlACQSwRZue3";

    private static SpotifyUtil spotifyUtil;

    public void init() {
        spotifyUtil = new SpotifyUtil(ACCESS_TOKEN);
    }

    @Test
    public void getUserTest() {
        init();
        assertNotNull(spotifyUtil.getUser());
    }

    @Test
    // @Disabled
    public void getPlayHistoryListens30Days() {
        init();
        List<Listens> listens = spotifyUtil.getPlayHistoryListens30Days(new User("1"));
        System.out.println(listens);
        assertNotNull(listens);
    }
}
