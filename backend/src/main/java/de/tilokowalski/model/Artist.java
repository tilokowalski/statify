package de.tilokowalski.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Artist {

    List<String> genres;


}
