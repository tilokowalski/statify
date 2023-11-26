package de.tilokowalski.utils;

import de.tilokowalski.mapper.SpotifyDataMapper;
import de.tilokowalski.model.Listens;
import de.tilokowalski.model.User;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Cursor;
import se.michaelthelin.spotify.model_objects.specification.PagingCursorbased;
import se.michaelthelin.spotify.model_objects.specification.PlayHistory;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;
import se.michaelthelin.spotify.requests.data.player.GetCurrentUsersRecentlyPlayedTracksRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

/**
 * The spotify util is used to fetch data from the spotify api.
 */
@Slf4j
public class SpotifyUtil {

    private final SpotifyDataMapper spotifyDataMapper = new SpotifyDataMapper();
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
     * Fetches the user data from the spotify api.
     *
     * @return The user data.
     */
    public User getUser() {
        GetCurrentUsersProfileRequest profileRequest = spotifyApi.getCurrentUsersProfile().build();

        try {
            return spotifyDataMapper.mapUserData(profileRequest.execute());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.atError().setCause(e).log("ERROR fetching user data");
        }

        return null;
    }

    /**
     * Fetches the play history data from the spotify api.
     *
     * @return The play history data.
     */
    public List<Listens> getPlayHistoryListens30Days(User user) {
        List<Listens> listens = new ArrayList<>();
        LocalDateTime today = LocalDateTime.now();

        LocalDateTime playedAt = today;
        while (today.minusDays(30).isBefore(playedAt)) {
            try {
                listens.add(createListens(playedAt, today, user));
            } catch (NullPointerException e){
                log.atError().setCause(e).log("ERROR");
                break;
            }
        }
        return listens;
    }

    /**
     * Returns the listens object for the relation.
     *
     * @return Listens object with data.
     */
    private Listens createListens(LocalDateTime playedAt, LocalDateTime today, User user) throws NullPointerException {
        PlayHistory[] playHistories;
        Pair<Cursor[], PlayHistory[]> pair = getPlayHistoryData(playedAt);
        playHistories = pair.getRight();
        for (PlayHistory playHistory : playHistories) {
            playedAt = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(Long.parseLong(pair.getLeft()[0].getAfter())),
                TimeZone.getDefault().toZoneId());
            if (today.minusDays(30).isBefore(playedAt)) {
                ArtistSimplified[] artists = playHistory.getTrack().getArtists();
                Map<String, List<String>>
                    artistGenres = new HashMap<>();
                Arrays.stream(artists).forEach((artist) ->
                    artistGenres.put(artist.getId(), getArtistGenres(artist.getId()))
                );
                return new Listens(user,
                    spotifyDataMapper.mapPlayHistoryData(playHistory, artistGenres),
                    playedAt);
            } else {
                break;
            }
        }
        return null;
    }

    /**
     * Returns the Play History in given timespan.
     *
     * @return PlayHistory[] with tracks
     */
    private Pair<Cursor[], PlayHistory[]> getPlayHistoryData(LocalDateTime before) {
        GetCurrentUsersRecentlyPlayedTracksRequest historyRequest =
            spotifyApi.getCurrentUsersRecentlyPlayedTracks()
                .before(DateUtil.convertToDate(before))
                .limit(50)
                .build();

        try {
            final PagingCursorbased<PlayHistory> playHistoryPagingCursorbased =
                historyRequest.execute();

            return Pair.of(playHistoryPagingCursorbased.getCursors(),
                playHistoryPagingCursorbased.getItems());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.atError().setCause(e).log("Fehler beim fetchen der Play History");
        }

        return null;
    }

    /**
     * Fetches the artist genre data from the spotify api.
     *
     * @return The genres.
     */
    private List<String> getArtistGenres(String artistId) {
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
}
