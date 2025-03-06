package me.lemonypancakes.races.user;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.UUID;

public final class UserDataDeserializer implements JsonDeserializer<User> {
    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        UUID uuid = UUID.fromString(obj.get("uuid").getAsString());
        boolean hasRaceBefore = obj.get("hasRaceBefore").getAsBoolean();

        return null;
    }
}
