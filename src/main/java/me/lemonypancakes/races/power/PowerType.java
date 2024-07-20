package me.lemonypancakes.races.power;

import me.lemonypancakes.races.Races;
import me.lemonypancakes.races.power.builtin.MobEffectPower;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Map;

public record PowerType<T extends Power>(NamespacedKey key, PowerFactory<T> factory) {
    public static final PowerType<MobEffectPower> MOB_EFFECT;

    private static <T extends Power> PowerType<T> register(String name, PowerFactory<T> factory) {
        return register(Races.namespace(name), factory);
    }

    private static <T extends Power> PowerType<T> register(NamespacedKey key, PowerFactory<T> factory) {
        return Registry.INSTANCE.register(new PowerType<>(key, factory));
    }

    static {
        MOB_EFFECT = register("mob_effect", MobEffectPower::new);
    }

    private enum Registry {
        INSTANCE;

        private final Map<NamespacedKey, PowerType<?>> registry;

        Registry() {
            registry = new HashMap<>();
        }

        public <T extends Power> PowerType<T> register(PowerType<T> powerType) {
            return (PowerType<T>) registry.putIfAbsent(powerType.key(), powerType);
        }

        public <T extends Power> PowerType<T> get(NamespacedKey key, PowerFactory<T> factory) {
            return (PowerType<T>) registry.get(key);
        }
    }
}
