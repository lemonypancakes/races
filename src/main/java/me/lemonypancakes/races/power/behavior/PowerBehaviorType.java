package me.lemonypancakes.races.power.behavior;

import me.lemonypancakes.races.Races;
import me.lemonypancakes.races.util.Unchecked;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Map;

public record PowerBehaviorType<T extends PowerBehavior<T>>(NamespacedKey key, PowerBehaviorFactory<T> factory) {
    public static final PowerBehaviorType<OverTimePowerBehavior> OVER_TIME;

    static {
        OVER_TIME = register("over_time", OverTimePowerBehavior.FACTORY);
    }

    public static <T extends PowerBehavior<T>> PowerBehaviorType<T> register(String name, PowerBehaviorFactory<T> factory) {
        return register(Races.namespace(name), factory);
    }

    public static <T extends PowerBehavior<T>> PowerBehaviorType<T> register(NamespacedKey key, PowerBehaviorFactory<T> factory) {
        return Registry.INSTANCE.register(new PowerBehaviorType<>(key, factory));
    }

    public static <T extends PowerBehavior<T>> PowerBehaviorType<T> get(NamespacedKey key) {
        return Registry.INSTANCE.get(key);
    }

    private enum Registry {
        INSTANCE;

        private final Map<NamespacedKey, PowerBehaviorType<?>> registry;

        Registry() {
            registry = new HashMap<>();
        }

        public <T extends PowerBehavior<T>> PowerBehaviorType<T> register(PowerBehaviorType<T> powerBehaviorType) {
            return Unchecked.cast(registry.putIfAbsent(powerBehaviorType.key(), powerBehaviorType));
        }

        public <T extends PowerBehavior<T>> PowerBehaviorType<T> get(NamespacedKey key) {
            return Unchecked.cast(registry.get(key));
        }
    }
}
