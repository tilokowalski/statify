package de.tilokowalski.db;

import java.util.Objects;

public abstract class Record {
    private transient String recordId;
    private transient String tableName;

    protected String recordId() {
        if (Objects.equals(recordId, "")) {
            return this.tableName;
        }
         return this.tableName + ":" + this.recordId;
    }


    protected Record(String tableName, String recordId) {
        this.tableName = tableName;
        this.recordId = recordId;
    }
}
