package de.tilokowalski.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * The artist model is used to store artist data.
 */
@Data
@Builder
public class Artist {

    /**
     * The genres of the artist.
     */
    List<String> genres;

}
