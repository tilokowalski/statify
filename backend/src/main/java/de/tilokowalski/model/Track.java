package de.tilokowalski.model;

import java.util.List;

import de.tilokowalski.db.Record;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Track extends Record {
    String title;
    List<Artist> artists;


    public Track(String title, List<Artist> artists) {
        this("", title, artists);
    }


    public Track(String recordId, String title, List<Artist> artists) {
        super("track", recordId);
        this.title = title;
        this.artists = artists;
    }
}
