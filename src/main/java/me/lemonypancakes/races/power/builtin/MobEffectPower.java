package me.lemonypancakes.races.power.builtin;

import com.google.gson.JsonObject;
import me.lemonypancakes.races.power.Power;
import me.lemonypancakes.races.power.PowerInstance;
import me.lemonypancakes.races.util.DataType;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import javax.annotation.Nonnull;

public class MobEffectPower extends Power {
    private final PotionEffect potionEffect;

    public MobEffectPower(NamespacedKey key, JsonObject json) {
        super(key, json);
        potionEffect = DataType.POTION_EFFECT.retrieve(json.get("potion").getAsJsonObject());
    }

    @Nonnull
    @Override
    protected PowerInstance<?> onApply(Player player) {
        return new Instance(this, player);
    }

    public static class Instance extends PowerInstance<MobEffectPower> {
        public Instance(MobEffectPower power, Player player) {
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
