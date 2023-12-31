package de.tilokowalski.model;

import de.tilokowalski.db.Thing;
import de.tilokowalski.db.RecordLink;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The track model is used to store track data.
 */
@Getter
@Setter
public class Track extends Thing {

    public static final String TABLE_NAME = "track";


    /**
     * The title of the track.
     */
    String title;

    /**
     * The artists of the track.
     */
    List<RecordLink<Artist>> artists;

    public Track(String recordId, String title) {
        this(recordId, title, new ArrayList<>());
    }

    /**
     * Creates a new track.
     *
     * @param recordId The record id.
     * @param title    The title of the track.
     * @param artists  The artists of the track.
     */
    public Track(String recordId, String title, List<RecordLink<Artist>> artists) {
        super(TABLE_NAME, recordId);
        this.title = title;
        this.artists = artists;
    }

    public void addArtists(Artist artist) {
        artists.add(RecordLink.create(artist));
    }
}
