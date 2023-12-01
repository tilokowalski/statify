package de.tilokowalski.spotify;

import com.surrealdb.connection.exception.SurrealException;
import com.surrealdb.connection.exception.SurrealRecordAlreadyExitsException;
import de.tilokowalski.db.Surreal;
import de.tilokowalski.model.Artist;
import de.tilokowalski.model.Listens;
import de.tilokowalski.model.Track;
import de.tilokowalski.model.User;
import de.tilokowalski.util.ToString;
import de.tilokowalski.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Cursor;
import se.michaelthelin.spotify.model_objects.specification.PagingCursorbased;
import se.michaelthelin.spotify.model_objects.specification.PlayHistory;
import se.michaelthelin.spotify.requests.data.artists.GetSeveralArtistsRequest;
import se.michaelthelin.spotify.requests.data.player.GetCurrentUsersRecentlyPlayedTracksRequest;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The spotify util is used to fetch data from the spotify api.
 */
@Slf4j
public class Spotify {

    /**
     * The spotify api is used to fetch data from the spotify api.
     */
    private static SpotifyApi spotifyApi;
    private final String accesToken;

    Surreal db;

    /**
     * Creates a new spotify util.
     *
     * @param accesToken The access token.
     */
    public Spotify(Surreal db, String accesToken) {
        this.accesToken = accesToken;
        this.db = db;
        initialize();
    }

    /**
     * Initializes the spotify api.
     */
    private void initialize() {
        spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accesToken)
                .build();
    }

    public void getPlayHistoryAndStore(User user) {
        getPlayHistoryAndStore(user, Duration.ofDays(30));
    }

    /**
     * Fetches the play history data from the spotify api.
     */
    public void getPlayHistoryAndStore(User user, Duration duration) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime after = now.minus(duration);

        LocalDateTime lastAfter = now;
        while (true) {
            Pair<Cursor, PlayHistory[]> pair = getPlayHistoryData(after);
            List<PlayHistory> playHistories = Arrays.asList(pair.getRight());

            // we fetched all available history data from spotify, so break loop
            if (playHistories.isEmpty() || pair.getLeft() == null) {
                return;
            }


            String afterUnix = pair.getLeft().getAfter();

            after = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(Long.parseLong(afterUnix)),
                    TimeZone.getDefault().toZoneId());

            // spotify api returns the same cursor once all items are fetched
            if (lastAfter.equals(after)) {
                break;
            }

            lastAfter = after;

            for (PlayHistory playHistory : playHistories) {
                processPlayHistory(user, playHistory);
            }
        }
    }

    private void processPlayHistory(User user, PlayHistory playHistory) {
        LocalDateTime playedAt = DateUtil.convertToLocalDateTime(playHistory.getPlayedAt());

        se.michaelthelin.spotify.model_objects.specification.Track spotifyTrack = playHistory.getTrack();
        Track track = new Track(spotifyTrack.getId(), spotifyTrack.getName());

        HashSet<String> artistIds = new HashSet<>();

        ArtistSimplified[] spotifyArtists = playHistory.getTrack().getArtists();
        for (ArtistSimplified spotifyArtist : spotifyArtists) {
            String id = spotifyArtist.getId();

            Artist artist = new Artist(id, spotifyArtist.getName());
            artistIds.add(id);

            // use record here to distinguish serialization.
            // TODO: This is not nice, since we can also pass an Artist object.
            // we should fetch the artist lazyily like jpas does.
            track.addArtists(artist);

        }

        try {
            db.store(track);
        } catch (SurrealException e) {
            if (e instanceof SurrealRecordAlreadyExitsException) {
                log.info("track " + ToString.create(track) + " already exists, skipping");
                return;
            } else {
                throw e;
            }
        }

        updateArtistsWithGenres(artistIds);

        Listens listens = new Listens(user, track, playedAt);
        db.relate(listens);
    }

    private void updateArtistsWithGenres(Set<String> artistIds) {
        se.michaelthelin.spotify.model_objects.specification.Artist[] spotifyArtists;

        GetSeveralArtistsRequest getArtistsRequest = spotifyApi.getSeveralArtists(artistIds.toArray(new String[0])).build();

        try {
            spotifyArtists = getArtistsRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.atError().setCause(e).log("Fehler beim fetchen der User Data");
            throw new RuntimeException();
        }

        for (se.michaelthelin.spotify.model_objects.specification.Artist spotifyArtist : spotifyArtists) {
            String id = spotifyArtist.getId();
            List<String> genres = Arrays.asList(spotifyArtist.getGenres());

            Artist artist = new Artist(id, spotifyArtist.getName(), genres);
            try {
                db.store(artist);
            } catch (SurrealException e) {
                if (e instanceof SurrealRecordAlreadyExitsException) {
                    log.info("artist + " + ToString.create(artist) + " already exists, skipping");
                    return;
                } else {
                    throw e;
                }
            }
        }
    }

    /**
     * Returns the Play History in given timespan.
     *
     * @return PlayHistory[] with tracks
     */
    private Pair<Cursor, PlayHistory[]> getPlayHistoryData(LocalDateTime after) {
        GetCurrentUsersRecentlyPlayedTracksRequest historyRequest = spotifyApi.getCurrentUsersRecentlyPlayedTracks()
                .after(DateUtil.convertToDate(after))
                .limit(50)
                .build();

        try {
            final PagingCursorbased<PlayHistory> playHistoryPagingCursorbased = historyRequest.execute();

            Cursor[] cursors = playHistoryPagingCursorbased.getCursors();
            if (cursors == null) {
                return Pair.of(null,
                        playHistoryPagingCursorbased.getItems());
            }

            return Pair.of(playHistoryPagingCursorbased.getCursors()[0],
                    playHistoryPagingCursorbased.getItems());

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.atError().setCause(e).log("Fehler beim fetchen der Play History");
            throw new RuntimeException(e);
        }
    }

    private User mapUserData(se.michaelthelin.spotify.model_objects.specification.User user) {
        return new User(user.getId());
    }
}
