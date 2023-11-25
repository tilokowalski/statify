package de.tilokowalski.utils;

import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

import org.apache.hc.core5.http.NotImplementedException;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.PagingCursorbased;
import se.michaelthelin.spotify.model_objects.specification.PlayHistory;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.data.player.GetCurrentUsersRecentlyPlayedTracksRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

/**
 * The spotify util is used to fetch data from the spotify api.
 */
@Slf4j
public class SpotifyUtil {

    /**
     * The access token is used to authenticate the requests.
     */
    private final String accesToken;

    /**
     * The spotify api is used to fetch data from the spotify api.
     */
    private static SpotifyApi spotifyApi;

    /**
     * Creates a new spotify util.
     *
     * @param accesToken The access token.
     */
    public SpotifyUtil(String accesToken) {
        this.accesToken = accesToken;
        initialize();
    }

    /**
     * Initializes the spotify api.
     */
    public void initialize() {
        spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accesToken)
            .build();
    }

    /**
     * Fetches the play history data from the spotify api.
     *
     * @return The play history data.
     */
    public PlayHistory[] getPlayHistoryData() {
        GetCurrentUsersRecentlyPlayedTracksRequest historyRequest = spotifyApi.getCurrentUsersRecentlyPlayedTracks()
            // .after(new Date())
            // .before(new Date())
            // .limit(0)
            .build();

        try {
            final PagingCursorbased<PlayHistory> playHistoryPagingCursorbased = historyRequest.execute();

            return playHistoryPagingCursorbased.getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.atError().setCause(e).log("Fehler beim fetchen der Play History");
        }

        return null;
    }

    /**
     * Fetches the user data from the spotify api.
     *
     * @return The user data.
     */
    public User getUser() {
        GetCurrentUsersProfileRequest profileRequest = spotifyApi.getCurrentUsersProfile().build();

        try {
            return profileRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.atError().setCause(e).log("Fehler beim fetchen der User Data");
        }

        return null;
    }

    /**
     * Fetches the play history data from the spotify api.
     *
     * @return The play history data.
     * @throws NotImplementedException Not implemented yet.
     */
    public Map<String, Track> getPlayHistoryData30Days() throws NotImplementedException {
        throw new NotImplementedException("Not implemented yet");
    }
}
