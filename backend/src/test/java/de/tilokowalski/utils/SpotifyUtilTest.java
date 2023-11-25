package de.tilokowalski.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class SpotifyUtilTest {

    private static SpotifyUtil spotifyUtil;

    public void init() {
        spotifyUtil = new SpotifyUtil("BQADWPrt3Djt_3y9Wgh1XYvk-OfbGOuuzJwIBpMlbhaBuwl-ESuVqOfFssygvq3oh61lRvE6MJcxVNGSEPDaMPl-IvQy8CfVMD2efsQH9orL0kDAKc-VdigDjDYWQb67np93duwQyYIfgJy3_QcWJNTZEbl1uAv2qHVWIIzJ_ln4vlCLAa2aKsKAyZuArDtLoeNNveugxFc");
    }

    @Test
    public void getPlayHistoryDataTest() {
        init();
        assertNotNull(spotifyUtil.getPlayHistoryData());
    }

    @Test
    public void getPlayHistoryDataUnauthorizedTest() {
        SpotifyUtil spotifyUtil = new SpotifyUtil("WRONG_ID");
        assertNull(spotifyUtil.getPlayHistoryData());
    }

    @Test
    public void getUserIdTest() {
        init();
        assertNotNull(spotifyUtil.getUser());
    }

    @Test
    @Disabled
    public void getPlayHistoryData30Days() {
        init();
        assertNotNull(spotifyUtil.getPlayHistoryData30Days());
    }
}
