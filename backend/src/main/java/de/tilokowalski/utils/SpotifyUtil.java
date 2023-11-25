package de.tilokowalski.utils;

import jakarta.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    @Inject
    SpotifyDataMapper mapper;

    private final String accesToken;

    private static SpotifyApi spotifyApi;

    public SpotifyUtil(String accesToken) {
        this.accesToken = accesToken;
        initialize();
    }

    /**
     * Initializes the Spotify Api Object.
     */
    public void initialize() {
        spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accesToken)
            .build();
    }

    /**
     * Returns the current spotify user.
     *
     * @return User object
     */
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

    /**
     * Returns the Play History in given timespan.
     *
     * @return PlayHistory[] with tracks
     */
    public PlayHistory[] getPlayHistoryData(LocalDateTime before) {
        GetCurrentUsersRecentlyPlayedTracksRequest historyRequest =
            spotifyApi.getCurrentUsersRecentlyPlayedTracks()
                .before(DateUtil.convertToDate(before))
                .limit(50)
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
     * Returns the Play History in given timespan.
     *
     * @return PlayHistory[] with tracks
     */
    public Map<String, Track> getPlayHistoryData30Days() {
        LocalDateTime before;
        Map<String, Track> mappedHistory = new HashMap<>();

        PlayHistory[] playHistories = getPlayHistoryData(LocalDateTime.now());

        Arrays.stream(playHistories)
            .forEach((playHistory) -> mappedHistory.put("Test" , mapper.map(playHistory)));

        before = DateUtil.convertToLocalDateTime(playHistories[playHistories.length-1].getPlayedAt());

        return mappedHistory;
    }
}
