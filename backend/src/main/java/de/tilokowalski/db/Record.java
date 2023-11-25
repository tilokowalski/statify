package de.tilokowalski.db;

import java.util.Objects;

/**
 * A record is a node in the surreal graph database.
 * It is identified by a table name and a record id.
 */
public abstract class Record {

    /**
     * The record id is optional.
     * If it is not set, the record id is the same as the table name.
     */
    private transient String recordId;

    /**
     * The table name is required.
     * It is used to identify the record.
     */
    private transient String tableName;

    /**
     * Used to generate the identifier used in creation statements.
     * The identifier preferably contains the record id, but if it is not set, the table name is used.
     *
     * @return The identifier used in creation statements.
     */
    protected String generateCreationIdentifier() {
        if (Objects.equals(recordId, "")) {
            return this.tableName;
        }

        return this.tableName + ":" + this.recordId;
    }

    /**
     * Creates a new record.
     *
     * @param tableName The table name.
     * @param recordId The record id.
     */
    protected Record(String tableName, String recordId) {
        this.tableName = tableName;
        this.recordId = recordId;
    }

}
