package me.lemonypancakes.races.power.builtin;

import com.google.gson.JsonObject;
import me.lemonypancakes.races.power.Power;
import me.lemonypancakes.races.power.PowerInstance;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class TickingPower extends Power {
    public TickingPower(NamespacedKey key, JsonObject json) {
        super(key, json);
    }

    @Nonnull
    @Override
    protected PowerInstance<?> onApply(Player player) {
        return new Instance(this, player);
    }

    public static class Instance extends PowerInstance<TickingPower> {
        public Instance(TickingPower power, Player player) {
            super(power, player);
        }

        @Override
        protected void onAdd() {
        }

        @Override
        protected void onRemove() {
        }
    }
}
