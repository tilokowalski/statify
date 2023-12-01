package de.tilokowalski.db;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(value = RecordLinkSerializer.class, nullSafe = false)
public class RecordLink<T extends Thing> {
    final String id;

    RecordLink(String reference) {
       this.id = reference;
    }

    public static <E extends Thing> RecordLink<E> create(E thing) {
        return new RecordLink<>(thing.toString());
    }

}
