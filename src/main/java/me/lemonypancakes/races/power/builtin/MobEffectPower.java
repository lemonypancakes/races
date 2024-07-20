package me.lemonypancakes.races.power.builtin;

import com.google.gson.JsonObject;
import me.lemonypancakes.races.power.Power;
import me.lemonypancakes.races.power.PowerInstance;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public class MobEffectPower extends Power {
    public MobEffectPower(NamespacedKey key, JsonObject json) {
        super(key, json);
    }

    @Override
    public void apply(Player player) {
        new Instance(this, player);
    }

    public static class Instance extends PowerInstance<MobEffectPower> {
        public Instance(MobEffectPower power, Player player) {
            super(power, player);
        }
    }
}
