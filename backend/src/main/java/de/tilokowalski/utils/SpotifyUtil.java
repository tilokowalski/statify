package de.tilokowalski.utils;

import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.PagingCursorbased;
import se.michaelthelin.spotify.model_objects.specification.PlayHistory;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.data.player.GetCurrentUsersRecentlyPlayedTracksRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

@Slf4j
public class SpotifyUtil {

    private final String accesToken;

    private static SpotifyApi spotifyApi;

    public SpotifyUtil(String accesToken) {
        this.accesToken = accesToken;
        initialize();
    }

    public void initialize() {
        spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accesToken)
            .build();
    }

    public PlayHistory[] getPlayHistoryData() {
        GetCurrentUsersRecentlyPlayedTracksRequest historyRequest =
            spotifyApi.getCurrentUsersRecentlyPlayedTracks()
//                .after(new Date())
//                .before(new Date())
//                .limit(0)
                .build();

        try {
            final PagingCursorbased<PlayHistory> playHistoryPagingCursorbased = historyRequest.execute();
            return playHistoryPagingCursorbased.getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.atError().setCause(e).log("Fehler beim fetchen der Play History");
        }
        return null;
    }

    public User getUser() {
        GetCurrentUsersProfileRequest profileRequest = spotifyApi.getCurrentUsersProfile()
            .build();

        try {
            return profileRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.atError().setCause(e).log("Fehler beim fetchen der User Data");
        }
        return null;
    }

    public Map<String, Track> getPlayHistoryData30Days() {


        return null;
    }
}
