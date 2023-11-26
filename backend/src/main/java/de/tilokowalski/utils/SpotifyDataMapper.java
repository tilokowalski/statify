package de.tilokowalski.utils;

import de.tilokowalski.model.Artist;
import de.tilokowalski.model.Track;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlayHistory;

@ApplicationScoped
public class SpotifyDataMapper {

//    public Track map(PlayHistory playHistory) {
//        Track track = new Track(playHistory.getTrack().getName(), null);
//
//        return track;
//    }
//
//    private List<Artist> mapArtists(PlayHistory playHistory) {
//        ArtistSimplified[] artistSimplified = playHistory.getTrack().getArtists();
//        List<Artist> artists = new ArrayList<>();
//
//        for(ArtistSimplified artist : artistSimplified) {
//            Artist a = Artist.builder().
//        }
//        return null;
//    }
}
