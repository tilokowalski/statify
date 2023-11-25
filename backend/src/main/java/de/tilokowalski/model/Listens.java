package de.tilokowalski.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Listens {
    LocalDateTime playedAt;
    User in;
    Track out;
}
