package de.tilokowalski.model;

import de.tilokowalski.db.Record;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * The track model is used to store track data.
 */
@Getter
@Setter
public class Track extends Record {

    /**
     * The title of the track.
     */
    String title;

    /**
     * The artists of the track.
     */
    List<Artist> artists;

    /**
     * Creates a new track.
     *
     * @param title The title of the track.
     * @param artists The artists of the track.
     */
    public Track(String title, List<Artist> artists) {
        this("", title, artists);
    }

    /**
     * Creates a new track.
     *
     * @param recordId The record id.
     * @param title The title of the track.
     * @param artists The artists of the track.
     */
    public Track(String recordId, String title, List<Artist> artists) {
        super(recordId);
        this.title = title;
        this.artists = artists;
    }

    @Override
    public String getTableName() {
        return "track";
    }
}
