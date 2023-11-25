package de.tilokowalski.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.tilokowalski.db.Relation;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * The listens relation is used to store the listens of a user.
 */
public class Listens extends Relation<User, Track> {

    /**
     * The time when the user listened to the track.
     */
    @Getter
    @Setter
    @JsonProperty("played_at")
    LocalDateTime playedAt;

    /**
     * Creates a new listens relation.
     *
     * @param in The incoming side of the relation.
     * @param out The outgoing side of the relation.
     * @param playedAt The time when the user listened to the track.
     */
    public Listens(User in, Track out, LocalDateTime playedAt) {
        super(in, out);
        this.playedAt = playedAt;
    }

    /**
     * The relation name is required.
     * It is used to identify the relation.
     *
     * @return The relation name.
     */
    public String relationName() {
        return "listens";
    }

}
