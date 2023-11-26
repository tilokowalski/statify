package de.tilokowalski.utils;

import de.tilokowalski.model.Artist;
import de.tilokowalski.model.Track;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PagingCursorbased;
import se.michaelthelin.spotify.model_objects.specification.PlayHistory;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;
import se.michaelthelin.spotify.requests.data.player.GetCurrentUsersRecentlyPlayedTracksRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

/**
 * The spotify util is used to fetch data from the spotify api.
 */
@Slf4j
public class SpotifyUtil {

    SpotifyDataMapper mapper = new SpotifyDataMapper();

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

    public List<String> getArtistGenres(String artistId) {
        se.michaelthelin.spotify.model_objects.specification.Artist artist;
        GetArtistRequest getArtistRequest = spotifyApi.getArtist(artistId).build();

        try {
             artist = getArtistRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.atError().setCause(e).log("Fehler beim fetchen der User Data");
            return null;
        }

        return Arrays.asList(artist.getGenres());
    }

    /**
     * Fetches the play history data from the spotify api.
     *
     * @return The play history data.
     */
    public List<Track> getPlayHistoryData30Days() {
        List<Track> mappedHistory = new ArrayList<>();
        LocalDateTime today = LocalDateTime.now();

        PlayHistory[] playHistories;

        LocalDateTime playedAt = today;
        while(today.minusDays(30).isBefore(playedAt)) {
            playHistories = getPlayHistoryData(playedAt);
            for (PlayHistory playHistory : playHistories) {
                playedAt = DateUtil.convertToLocalDateTime(playHistory.getPlayedAt());
                if(today.minusDays(30).isBefore(playedAt)) {
                    mappedHistory.add(mapPlayHistoryData(playHistory));
                } else {
                    break;
                }
            }
            if (!today.minusDays(30).isBefore(playedAt)) break;
        }

        return mappedHistory;
    }

    private Track mapPlayHistoryData(PlayHistory playHistory) {
        ArtistSimplified[] artistSimplified = playHistory.getTrack().getArtists();
        List<Artist> artists = new ArrayList<>();

        for(ArtistSimplified simplifiedArtist : artistSimplified) {
            Artist artist = Artist.builder()
                .genres(getArtistGenres(simplifiedArtist.getId()))
                .build();

            artists.add(artist);
        }
        return new Track(playHistory.getTrack().getId() ,playHistory.getTrack().getName(), artists);
    }
}
