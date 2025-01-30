package me.lemonypancakes.races.condition;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import me.lemonypancakes.races.Races;
import me.lemonypancakes.races.serialization.DataSchema;
import me.lemonypancakes.races.util.Unchecked;
import org.bukkit.NamespacedKey;

public record ConditionType<T>(Class<T> typeClass, NamespacedKey key, ConditionFactory<T> factory) {
  public static <T> ConditionType<T> register(
      Class<T> typeClass, String name, ConditionFactory<T> factory) {
    return register(typeClass, Races.namespace(name), factory);
  }

  public static <T> ConditionType<T> registerSimple(
      Class<T> typeClass, String name, DataSchema schema, Predicate<T> condition) {
    return registerSimple(typeClass, Races.namespace(name), schema, condition);
  }

  public static <T> ConditionType<T> registerSimple(
      Class<T> typeClass, NamespacedKey key, DataSchema schema, Predicate<T> condition) {
    return register(
        typeClass,
        key,
        new ConditionFactory<>(
            schema,
            container ->
                new Condition<>() {
                  @Override
                  public boolean test(T t) {
                    return condition.test(t);
                  }
                }));
  }

  public static <T> ConditionType<T> register(
      Class<T> typeClass, NamespacedKey key, ConditionFactory<T> factory) {
    return register(new ConditionType<>(typeClass, key, factory));
  }

  public static <T> ConditionType<T> register(ConditionType<T> conditionType) {
    return Registry.INSTANCE.register(conditionType);
  }

  public static <T> ConditionType<T> get(Class<T> typeClass, NamespacedKey key) {
    return Registry.INSTANCE.get(typeClass, key);
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
      if (conditionTypeMap.containsKey(key)) return Unchecked.cast(conditionTypeMap.get(key));
      return null;
    }
  }
}
