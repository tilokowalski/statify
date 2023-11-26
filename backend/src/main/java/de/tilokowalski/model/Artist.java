package de.tilokowalski.model;

import java.util.List;

import de.tilokowalski.db.Record;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
public class Artist extends Record {

    private static final String TABLE_NAME = "artist";

    /**
     * The genres of the artist.
     */
    @Setter
    List<String> genres;

    String name;

    public Artist(String recordId, String name) {
        this(recordId, name, null);
    }


    public Artist(String recordId, String name, List<String> genres) {
        super(TABLE_NAME, recordId);
        this.name = name;
        this.genres = genres;
    }

}
