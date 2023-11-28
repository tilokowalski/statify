package de.tilokowalski.db;

import com.google.gson.*;

import java.lang.reflect.Type;

public class RecordLinkSerializer implements JsonSerializer<RecordLink<?>>, JsonDeserializer<RecordLink<?>> {
    @Override
    public RecordLink<?> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String id = jsonElement.getAsJsonPrimitive().getAsString();
            return new RecordLink<>(id);
    }

    @Override
    public JsonElement serialize(RecordLink recordLink, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(recordLink.id);
    }
}
