package me.lemonypancakes.races.util;

import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.potion.PotionEffect;

import java.util.function.Function;

public record DataType<T>(Function<JsonObject, T> function) {
    public static final DataType<PotionEffect> POTION_EFFECT = new DataType<>(json -> new PotionEffect(
            Registry.EFFECT.get(NamespacedKey.fromString(json.get("potion_effect").toString())),
            json.get("duration").getAsInt(),
            json.get("amplifier").getAsInt(),
            !json.has("ambient") || json.get("ambient").getAsBoolean(),
            !json.has("particles") || json.get("particles").getAsBoolean(),
            !json.has("icon") || json.get("icon").getAsBoolean()));

    public T retrieve(JsonObject json) {
        return function.apply(json);
    }
}
