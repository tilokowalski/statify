package de.tilokowalski.model;

import de.tilokowalski.db.Thing;
import lombok.Getter;
import lombok.Setter;

/**
 * The user model is used to store user data.
 */
@Getter
@Setter
public class User extends Thing {

    public static final String TABLE_NAME = "user";
    /**
     * Creates a new user.
     *
     */
    public User() {
        this(TABLE_NAME);
    }


    /**
     * Creates a new user.
     *
     * @param recordId The record id.
     */
    public User(String recordId) {
        super(TABLE_NAME, recordId);
    }

}
