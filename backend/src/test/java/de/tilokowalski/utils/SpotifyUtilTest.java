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
        spotifyUtil = new SpotifyUtil("BQAzXMmOxjuc1x4JL9vZ_MFbxy_8HDDjqH5W9ulDrjUUo83L5xI9DS2UhVPECyaaVAm9pUDbei4Ft9N4AQNK2Ps7VQUc2ePovFa5ycxIwRR3-B-WpVDZzZXgTYMhsQFAw3k98cLn0M9K51sOhuMqrtvKieL1rtPUkq0z-AytEjMovUFat_CRvikeBgWqcpTHYLHf0dyW0Fs");
    }

    @Test
    @Disabled
    public void getPlayHistoryDataTest() {
        init();
        assertNotNull(spotifyUtil.getPlayHistoryData());
    }

    @Test
    @Disabled
    public void getPlayHistoryDataUnauthorizedTest() {
        SpotifyUtil spotifyUtil = new SpotifyUtil("WRONG_ID");
        assertNull(spotifyUtil.getPlayHistoryData());
    }

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
