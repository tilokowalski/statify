package de.tilokowalski.mapper;

import de.tilokowalski.model.Artist;
import de.tilokowalski.model.Track;
import de.tilokowalski.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlayHistory;

@ApplicationScoped
public class SpotifyDataMapper {

    public Track mapPlayHistoryData(PlayHistory playHistory,
        Map<String, List<String>> artistsGenres) {
        ArtistSimplified[] artistSimplified = playHistory.getTrack().getArtists();
        List<Artist> artists = new ArrayList<>();

        for (ArtistSimplified simplifiedArtist : artistSimplified) {
            Artist artist = new Artist(simplifiedArtist.getId(), simplifiedArtist.getName(),
                artistsGenres.get(simplifiedArtist.getId()));
            artists.add(artist);
        }
        return new Track(playHistory.getTrack().getId(), playHistory.getTrack().getName(), artists);
    }

    public User mapUserData(se.michaelthelin.spotify.model_objects.specification.User user) {
        return new User(user.getId(), user.getDisplayName());
    }
}
