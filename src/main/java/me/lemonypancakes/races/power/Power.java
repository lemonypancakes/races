package me.lemonypancakes.races.power;

import com.google.gson.*;
import me.lemonypancakes.races.power.behavior.OverTimePowerBehavior;
import me.lemonypancakes.races.power.behavior.PowerBehavior;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;

public record Power(NamespacedKey key, String name, String displayName, String description,
                    ItemStack icon, int impact, int order, PowerBehavior<?> behavior) implements JsonDeserializer<Power>, JsonSerializer<Power> {
    public static final Power EMPTY;

    static {
        EMPTY = new Power(null, null, null, null, null, 0, 0, new OverTimePowerBehavior(null));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Power power)) return false;

        return key.equals(power.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return key.toString();
    }

    @Override
    public Power deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }

    @Override
    public JsonElement serialize(Power power, Type type, JsonSerializationContext jsonSerializationContext) {
        return null;
    }
}
