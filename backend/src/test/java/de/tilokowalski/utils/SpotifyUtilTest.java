package de.tilokowalski.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.tilokowalski.model.User;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class SpotifyUtilTest {

    private static final String ACCESS_TOKEN = "BQAItNXaKX-tRLFfKCn3EnTpvikhopFJbFJXYARHpmYcE47eHmg5UbV2IVE-bL0hj1ZFJugAqbCE4kZIiDS9YKipm1NM9XzpjVrWFYM9dl_lxzytSzhXRMR-kDQ565defMdmHtf_VVMj5rsj_uSRmLp5-WQFBGWr3s4xVchW6dseuQpOyfikjJ3sLV4HpSaCotOV4MdTc-8";

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
        assertNotNull(spotifyUtil.getPlayHistoryListens30Days(new User("1")));
    }
}
