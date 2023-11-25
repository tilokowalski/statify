package de.tilokowalski.model;

import java.util.List;
import lombok.Data;

@Data
public class Track {
    String title;
    List<Artist> artists;
}
