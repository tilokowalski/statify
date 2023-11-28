package de.tilokowalski.db;

import lombok.NonNull;

/**
 * A relation is an edge in the surreal graph database.
 * It is identified by a relation name and two records.
 */
public abstract class Relation<T extends Thing, R extends Thing> {

    /**
     * The incoming side of the relation.
     */
    @NonNull
    private final transient T in;

    /**
     * The outgoing side of the relation.
     */
    @NonNull
    private final transient R out;

    /**
     * Creates a new relation.
     *
     * @param in The incoming side of the relation.
     * @param out The outgoing side of the relation.
     */
    protected Relation(T in, R out) {
        this.in = in;
        this.out = out;
    }

    /**
     * The relation name is required.
     * It is used to identify the relation.
     *
     * @return The relation name.
     */
    protected abstract String relationName();

    /**
     * Returns the incoming side of the relation.
     *
     * @return The incoming side of the relation.
     */
    public T in() {
        return in;
    }

    /**
     * Returns the outgoing side of the relation.
     *
     * @return The outgoing side of the relation.
     */
    public R out() {
        return out;
    }

}
