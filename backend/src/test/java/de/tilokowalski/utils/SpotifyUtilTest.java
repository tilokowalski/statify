package de.tilokowalski.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.tilokowalski.model.User;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class SpotifyUtilTest {

    private static final String ACCESS_TOKEN = "BQBBGUQLLTnm3Gu5fmSS4LYfNmje_lcvjZhKCu1rzRLtLftqXW5Thme5liabRN6lNDWPsUbZXA198ZIR9md4cx3dDEEiFInAtxWzeJaB7C7yYHBJHqZQkYmXMEyrerCGkJ_XzrF7FA_4LxS5dsp7vop9KX4e-0JQOtIAkKJVPIcmzkpSgYVIP1Li5cwiwtOKKSqSxNzsc10";

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
    @Disabled
    public void getPlayHistoryListens30Days() {
        init();
        assertNotNull(spotifyUtil.getPlayHistoryListens30Days(new User("1", "User")));
    }
}
