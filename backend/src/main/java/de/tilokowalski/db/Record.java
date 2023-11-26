package de.tilokowalski.db;

import java.util.Objects;

/**
 * A record is a node in the surreal graph database.
 * It is identified by a table name and a record id.
 */
public abstract class Record implements RecordTableName {

    /**
     * The record id is optional.
     * If it is not set, the record id is the same as the table name.
     */
    private final transient String recordId;


    /**
     * Used to generate the identifier used in creation statements.
     * The identifier preferably contains the record id, but if it is not set, the table name is used.
     *
     * @return The identifier used in creation statements.
     */
    public String generateCreationIdentifier() {
        if (Objects.equals(recordId, "")) {
            return getTableName();
        }
         return getTableName() + ":" + this.recordId;
    }

    /**
     * Creates a new record.
     *
     * @param recordId The record id.
     */
    protected Record(String recordId) {
        this.recordId = recordId;
    }

}
