package de.tilokowalski.utils;

import com.google.common.collect.Iterables;
import de.tilokowalski.model.Artist;
import de.tilokowalski.model.Listens;
import de.tilokowalski.model.Track;
import de.tilokowalski.model.User;
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
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The spotify util is used to fetch data from the spotify api.
 */
@Slf4j
public class SpotifyUtil {

    /**
     * The spotify api is used to fetch data from the spotify api.
     */
    private static SpotifyApi spotifyApi;
    private final String accesToken;

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
            return mapUserData(profileRequest.execute());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.atError().setCause(e).log("ERROR fetching user data");
            throw new RuntimeException();
        }
    }

    public List<Listens> getPlayHistoryListens(User user) {
        return getPlayHistoryListens(user, Duration.ofDays(30));
    }

    /**
     * Fetches the play history data from the spotify api.
     *
     * @return The play history data.
     */
    public List<Listens> getPlayHistoryListens(User user, Duration duration) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime nextCursorDate = LocalDateTime.now();

        HashMap<Artist, List<Listens>> artistListensMap = new HashMap<>();
        HashMap<String, Artist> artistMap = new HashMap<>();

        outer:
        do {
            Pair<Cursor[], PlayHistory[]> pair = getPlayHistoryData(nextCursorDate);
            List<PlayHistory> playHistories = Arrays.asList(pair.getRight());

            // we fetched all available history data from spotify, so break loop
            if (playHistories.isEmpty() || pair.getLeft().length == 0) {
                break;
            }

            String nextCursorDateUnix = pair.getLeft()[0].getAfter();

            nextCursorDate = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(Long.parseLong(nextCursorDateUnix)),
                    TimeZone.getDefault().toZoneId());


            for (PlayHistory playHistory : playHistories) {
                LocalDateTime playedAt = DateUtil.convertToLocalDateTime(playHistory.getPlayedAt());
                if (today.minusDays(duration.toDays()).isBefore(playedAt)) {
                    Track track = new Track(playHistory.getTrack().getId(), playHistory.getTrack().getName());

                    ArtistSimplified[] artistSimplified = playHistory.getTrack().getArtists();
                    Listens listens = new Listens(user, track, playedAt);

                    for (ArtistSimplified simplifiedArtist : artistSimplified) {
                        String id = simplifiedArtist.getId();

                        Optional.ofNullable(artistMap.get(id)).ifPresentOrElse(
                                artist -> {
                                    track.addArtists(artist);
                                    var listensList = artistListensMap.get(artist);
                                    listensList.add(listens);
                                },
                                () -> {
                                    Artist artist = new Artist(simplifiedArtist.getId(), simplifiedArtist.getName());
                                    track.addArtists(artist);
                                    artistMap.put(id, artist);
                                    artistListensMap.put(artist, new ArrayList<>(List.of(listens)));
                                }
                        );
                    }

                    //listens.add(new Listens(user, mapPlayHistoryData(playHistory), playedAt));
                } else {
                    break outer;
                }
            }

            //List<String> artistIds = artistMap.keySet().stream().toList();
            Iterables.partition(artistMap.keySet(), 50).forEach(artistsChunk -> {
                Map<String, List<String>> artistsGenres = getArtistsGenres(artistsChunk);

                for (String artistId : artistsChunk) {
                    List<String> genres = artistsGenres.get(artistId);
                    if (Objects.isNull(genres)) {
                        continue;
                    }

                    Artist artist = artistMap.get(artistId);
                    artist.setGenres(genres);
                }
            });

            // map genres to artists
            // map artists to tracks


        } while (today.minusDays(duration.toDays()).isBefore(nextCursorDate));

        return artistListensMap.values().stream().flatMap(List::stream).toList();
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
            throw new RuntimeException(e);
        }
    }

    /**
     * Fetches the artist genre data from the spotify api.
     *
     * @return a map with key as the artist id and value is genres.
     */
    private Map<String, List<String>> getArtistsGenres(List<String> artistIds) {
        se.michaelthelin.spotify.model_objects.specification.Artist[] spotifyArtists;

        GetSeveralArtistsRequest getArtistsRequest = spotifyApi.getSeveralArtists(artistIds.toArray(new String[0])).build();


        try {
            spotifyArtists = getArtistsRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.atError().setCause(e).log("Fehler beim fetchen der User Data");
            throw new RuntimeException();
        }

        Map<String, List<String>> artistGenres = new HashMap<>(spotifyArtists.length);
        for (se.michaelthelin.spotify.model_objects.specification.Artist artist : spotifyArtists) {
            artistGenres.put(artist.getId(), Arrays.asList(artist.getGenres()));
        }
        return artistGenres;
    }

    private Track mapPlayHistoryData(PlayHistory playHistory) {
        ArtistSimplified[] artistSimplified = playHistory.getTrack().getArtists();
        List<Artist> artists = new ArrayList<>();

        for (ArtistSimplified simplifiedArtist : artistSimplified) {
            Artist artist = new Artist(simplifiedArtist.getId(), simplifiedArtist.getName());
            artists.add(artist);
        }
        return new Track(playHistory.getTrack().getId(), playHistory.getTrack().getName(), artists);
    }

    private User mapUserData(se.michaelthelin.spotify.model_objects.specification.User user) {
        return new User(user.getId());
    }
}
