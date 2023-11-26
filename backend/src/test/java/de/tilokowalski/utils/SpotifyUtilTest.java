package de.tilokowalski.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class SpotifyUtilTest {

    private static final String ACCESS_TOKEN = "BQDWAPFIr3x-la1Zh0XgyJ6TLXlGO1U0MADSdiZbOJBXbZTSfGE5xPi9CfyV1WQu12EKHNt1dNo4aIlBMX2GuKL7-8cNhAxAa3gxdGgE13WVkPhuEedQ_GHv5VEHYQKXHlC5lRMRkJGVv51cA8VyxLQri1WjaqfogoFW6GP5an6uK7K_5eFKxpAwHqJ7tyHnSjuFI3XK7D0";

    private static SpotifyUtil spotifyUtil;

    public void init() {
        spotifyUtil = new SpotifyUtil(ACCESS_TOKEN);
    }

//    @Test
//    @Disabled
//    public void getPlayHistoryDataTest() {
//        init();
//        assertNotNull(spotifyUtil.getPlayHistoryData());
//    }
//
//    @Test
//    @Disabled
//    public void getPlayHistoryDataUnauthorizedTest() {
//        SpotifyUtil spotifyUtil = new SpotifyUtil("WRONG_ID");
//        assertNull(spotifyUtil.getPlayHistoryData());
//    }

    @Test
    public void getUserTest() {
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
