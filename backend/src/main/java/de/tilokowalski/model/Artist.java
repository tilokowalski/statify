package de.tilokowalski.model;

import java.util.List;

import de.tilokowalski.db.Thing;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Artist extends Thing {

    public static final String TABLE_NAME = "artist";

    /**
     * The genres of the artist.
     */
    List<String> genres;

    String name;

    public Artist(String recordId) {
        this(recordId, null, null);
    }

    public Artist(String recordId, String name) {
        this(recordId, name, null);
    }


    public Artist(String recordId, String name, List<String> genres) {
        super(TABLE_NAME, recordId);
        this.name = name;
        this.genres = genres;
    }

}
