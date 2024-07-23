package me.lemonypancakes.races.power;

import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;

@FunctionalInterface
public interface PowerFactory<T extends Power> {
    T create(NamespacedKey key, JsonObject json);
}
