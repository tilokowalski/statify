package de.tilokowalski.model;

import de.tilokowalski.db.Record;
import lombok.Getter;
import lombok.Setter;

/**
 * The user model is used to store user data.
 */
@Getter
@Setter
public class User extends Record {

    /**
     * The name of the user.
     */
    String name;

    /**
     * Creates a new user.
     *
     * @param name The name of the user.
     */
    public User(String name) {
        this("", name);
    }

    /**
     * Creates a new user.
     *
     * @param recordId The record id.
     * @param name The name of the user.
     */
    public User(String recordId, String name) {
        super("user", recordId);
        this.name = name;
    }
    
}
