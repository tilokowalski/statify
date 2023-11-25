package de.tilokowalski.model;

import java.util.List;

import de.tilokowalski.db.Record;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Getter
public class Artist extends Record {

    /**
     * The genres of the artist.
     */
    List<String> genres;

    String name;

    public Artist(String name) {
        this("", name);
    }


    public Artist(String recordId, String name) {
        super(recordId);
        this.name = name;
    }


    @Override
    public String getTableName() {
        return "artist";
    }
}
