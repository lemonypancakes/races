package me.lemonypancakes.races.util;

import com.google.gson.JsonObject;
import me.lemonypancakes.races.action.Action;
import me.lemonypancakes.races.action.ActionType;
import org.bukkit.NamespacedKey;

public class ActionUtil {

    public static <T> Action<T> get(Class<T> clazz, JsonObject data) {
        if (!data.has("type")) {
            throw new IllegalArgumentException("Action type not specified");
        }
        NamespacedKey key = NamespacedKey.fromString(data.get("type").getAsString());

        if (key == null) {
            throw new IllegalArgumentException("Invalid action type");
        }
        ActionType<T> type = ActionType.get(clazz, key);
        if (type == null) {
            throw new IllegalArgumentException("Invalid action type");
        }
        return type.factory().create(data);
    }
}
