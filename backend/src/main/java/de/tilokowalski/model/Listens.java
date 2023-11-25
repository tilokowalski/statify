package de.tilokowalski.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.tilokowalski.db.Relation;
import io.smallrye.common.constraint.NotNull;
import lombok.*;

public class Listens extends Relation<User, Track> {

    @JsonProperty("played_at")
            @Getter
            @Setter
    LocalDateTime playedAt;

    public Listens(User in, Track out, LocalDateTime playedAt) {
        super(in, out);
        this.playedAt = playedAt;
    }


    public String relationName() {
        return "listens";
    }


}
