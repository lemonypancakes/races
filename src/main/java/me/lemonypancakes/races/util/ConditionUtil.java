package me.lemonypancakes.races.util;

import com.google.gson.JsonObject;
import me.lemonypancakes.races.condition.Condition;
import me.lemonypancakes.races.condition.ConditionType;
import org.bukkit.NamespacedKey;

public class ConditionUtil {

    public static <T> Condition<T> get(Class<T> clazz, JsonObject data) {
        if (!data.has("type")) {
            throw new IllegalArgumentException("Condition type not specified");
        }
        NamespacedKey key = NamespacedKey.fromString(data.get("type").getAsString());

        if (key == null) {
            throw new IllegalArgumentException("Invalid condition type");
        }
        ConditionType<T> type = ConditionType.get(clazz, key);
        if (type == null) {
            throw new IllegalArgumentException("Invalid condition type");
        }
        return type.factory().create(data);
    }
}
