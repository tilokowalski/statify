package de.tilokowalski.db;

import lombok.NonNull;

public abstract class Relation<T extends Record, R extends Record> {

    @NonNull
    private final transient T in;
    @NonNull
    private final transient R out;

    protected Relation(T in, R out) {
        this.in = in;
        this.out = out;
    }

    protected abstract String relationName();

    protected  T in() {
    return in;
    }
    protected  R out() {
return out;
    }


}
