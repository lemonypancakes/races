package me.lemonypancakes.races.condition;

import com.google.gson.JsonObject;
import me.lemonypancakes.races.Races;
import me.lemonypancakes.races.util.Unchecked;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

public record ConditionType<T>(Class<T> typeClass, NamespacedKey key, ConditionFactory<T> factory) {
    public static final ConditionType<Entity> TEST;

    public static <T> ConditionType<T> register(Class<T> typeClass, String name, ConditionFactory<T> factory) {
        return register(typeClass, Races.namespace(name), factory);
    }

    public static <T> ConditionType<T> registerSimple(Class<T> typeClass, String name, BiPredicate<JsonObject, T> condition) {
        return registerSimple(typeClass, Races.namespace(name), condition);
    }

    public static <T> ConditionType<T> registerSimple(Class<T> typeClass, NamespacedKey key, BiPredicate<JsonObject, T> condition) {
        return register(typeClass, key, json -> new Condition<>(json) {
            @Override
            public boolean test(T t) {
                return condition.test(json, t);
            }
        });
    }

    public static <T> ConditionType<T> register(Class<T> typeClass, NamespacedKey key, ConditionFactory<T> factory) {
        return register(new ConditionType<>(typeClass, key, factory));
    }

    public static <T> ConditionType<T> register(ConditionType<T> conditionType) {
        return Registry.INSTANCE.register(conditionType);
    }


    public static <T> ConditionType<T> get(Class<T> typeClass, NamespacedKey key) {
        return Registry.INSTANCE.get(typeClass, key);
    }

    static {
        TEST = registerSimple(Entity.class, "test", (json, entity) -> entity.isDead());
    }

    private enum Registry {
        INSTANCE;

        private final Map<Class<?>, Map<NamespacedKey, ConditionType<?>>> registry;

        Registry() {
            registry = new HashMap<>();
        }

        public <T> ConditionType<T> register(ConditionType<T> conditionType) {
            Class<T> typeClass = conditionType.typeClass;
            if (!registry.containsKey(typeClass)) registry.put(typeClass, new HashMap<>());
            registry.get(typeClass).putIfAbsent(conditionType.key, conditionType);
            return conditionType;
        }

        public <T> ConditionType<T> get(Class<T> typeClass, NamespacedKey key) {
            if (!registry.containsKey(typeClass)) return null;
            Map<NamespacedKey, ConditionType<?>> conditionTypeMap = registry.get(typeClass);
            if (conditionTypeMap.containsKey(key)) return Unchecked.unchecked(conditionTypeMap.get(key));
            return null;
        }
    }
}
