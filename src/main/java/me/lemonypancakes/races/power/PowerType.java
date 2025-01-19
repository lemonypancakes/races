package me.lemonypancakes.races.power;

import me.lemonypancakes.races.Races;
import me.lemonypancakes.races.util.Unchecked;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Map;

public record PowerType<T extends Power>(NamespacedKey key, PowerFactory<T> factory) {
    public static final PowerType<PotionEffectPower> MOB_EFFECT;

    public static <T extends Power> PowerType<T> register(String name, PowerFactory<T> factory) {
        return register(Races.namespace(name), factory);
    }

    public static <T extends Power> PowerType<T> register(NamespacedKey key, PowerFactory<T> factory) {
        return Registry.INSTANCE.register(new PowerType<>(key, factory));
    }

    public static <T extends Power> PowerType<T> get(NamespacedKey key) {
        return Registry.INSTANCE.get(key);
    }

    static {
        MOB_EFFECT = register("mob_effect", PotionEffectPower::new);
    }

    private enum Registry {
        INSTANCE;

        private final Map<NamespacedKey, PowerType<?>> registry;

        Registry() {
            registry = new HashMap<>();
        }

        public <T extends Power> PowerType<T> register(PowerType<T> powerType) {
            return Unchecked.unchecked(registry.putIfAbsent(powerType.key(), powerType));
        }

        public <T extends Power> PowerType<T> get(NamespacedKey key) {
            return Unchecked.unchecked(registry.get(key));
        }
    }
}
