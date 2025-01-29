package me.lemonypancakes.races.power.behavior;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

public abstract class PowerBehavior<T extends PowerBehavior<T>> {
    private final JsonObject data;

    public PowerBehavior(JsonObject data) {
        this.data = data;
    }

    public final JsonObject getData() {
        return data.deepCopy();
    }

    public abstract PowerBehaviorInstance<T> apply(Player player);
}
