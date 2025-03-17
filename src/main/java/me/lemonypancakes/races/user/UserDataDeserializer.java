package me.lemonypancakes.races.user;

import com.google.gson.*;
import org.bukkit.NamespacedKey;

import java.lang.reflect.Type;
import java.util.*;

public final class UserDataDeserializer implements JsonDeserializer<UserData> {
  @Override
  public UserData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    if (!json.isJsonObject()) {
      throw new JsonParseException("Expected JSON object for User deserialization.");
    }

    JsonObject object = json.getAsJsonObject();

    UUID uuid = getUUID(object, "uuid");
    boolean hasRaceBefore = getBoolean(object, "hasRaceBefore");
    Map<NamespacedKey, List<NamespacedKey>> powers = parsePowers(object.getAsJsonObject("powers"));
    Map<NamespacedKey, NamespacedKey> races = parseRaces(object.getAsJsonObject("races"));

    return new UserData(uuid, powers, races, hasRaceBefore);
  }

  // Safely retrieve UUID
  private UUID getUUID(JsonObject object, String key) {
    return object.has(key) && !object.get(key).isJsonNull()
        ? UUID.fromString(object.get(key).getAsString())
        : UUID.randomUUID(); // Fallback to random UUID if missing
  }

  // Safely retrieve boolean
  private boolean getBoolean(JsonObject object, String key) {
    return object.has(key) && object.get(key).isJsonPrimitive()
        ? object.get(key).getAsBoolean()
        : false; // Default to false if missing
  }

  // Optimized races parsing
  private Map<NamespacedKey, NamespacedKey> parseRaces(JsonObject racesObject) {
    Map<NamespacedKey, NamespacedKey> races = new HashMap<>();
    if (racesObject == null) return races;

    for (Map.Entry<String, JsonElement> entry : racesObject.entrySet()) {
      NamespacedKey key = NamespacedKey.fromString(entry.getKey());
      NamespacedKey value =
          entry.getValue().isJsonPrimitive()
              ? NamespacedKey.fromString(entry.getValue().getAsString())
              : null;

      if (key != null && value != null) {
        races.put(key, value);
      }
    }
    return races;
  }

  // Optimized powers parsing
  private Map<NamespacedKey, List<NamespacedKey>> parsePowers(JsonObject powersObject) {
    Map<NamespacedKey, List<NamespacedKey>> powers = new HashMap<>();
    if (powersObject == null) return powers;

    for (Map.Entry<String, JsonElement> entry : powersObject.entrySet()) {
      NamespacedKey key = NamespacedKey.fromString(entry.getKey());
      List<NamespacedKey> values = new ArrayList<>();

      if (key != null && entry.getValue().isJsonArray()) {
        for (JsonElement elem : entry.getValue().getAsJsonArray()) {
          if (elem.isJsonPrimitive()) {
            NamespacedKey val = NamespacedKey.fromString(elem.getAsString());
            if (val != null) values.add(val);
          }
        }
        powers.put(key, values);
      }
    }
    return powers;
  }
}
