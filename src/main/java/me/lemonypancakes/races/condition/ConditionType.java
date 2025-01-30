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
      final Class<T> typeClass, final String name, final ConditionFactory<T> factory) {
    return register(typeClass, Races.namespace(name), factory);
  }

  public static <T> ConditionType<T> registerSimple(
      final Class<T> typeClass,
      final String name,
      final DataSchema schema,
      final Predicate<T> condition) {
    return registerSimple(typeClass, Races.namespace(name), schema, condition);
  }

  public static <T> ConditionType<T> registerSimple(
      final Class<T> typeClass,
      final NamespacedKey key,
      final DataSchema schema,
      final Predicate<T> condition) {
    return register(
        typeClass,
        key,
        new ConditionFactory<>(
            schema,
            container ->
                new Condition<>() {
                  @Override
                  public boolean test(final T t) {
                    return condition.test(t);
                  }
                }));
  }

  public static <T> ConditionType<T> register(
      final Class<T> typeClass, final NamespacedKey key, final ConditionFactory<T> factory) {
    return register(new ConditionType<>(typeClass, key, factory));
  }

  public static <T> ConditionType<T> register(final ConditionType<T> conditionType) {
    return Registry.INSTANCE.register(conditionType);
  }

  public static <T> ConditionType<T> get(final Class<T> typeClass, final NamespacedKey key) {
    return Registry.INSTANCE.get(typeClass, key);
  }

  private enum Registry {
    INSTANCE;

    private final Map<Class<?>, Map<NamespacedKey, ConditionType<?>>> registry;

    Registry() {
      this.registry = new HashMap<>();
    }

    public <T> ConditionType<T> register(final ConditionType<T> conditionType) {
      final Class<T> typeClass = conditionType.typeClass;
      if (!this.registry.containsKey(typeClass)) this.registry.put(typeClass, new HashMap<>());
      this.registry.get(typeClass).putIfAbsent(conditionType.key, conditionType);
      return conditionType;
    }

    public <T> ConditionType<T> get(final Class<T> typeClass, final NamespacedKey key) {
      if (!this.registry.containsKey(typeClass)) return null;
      final Map<NamespacedKey, ConditionType<?>> conditionTypeMap = this.registry.get(typeClass);
      if (conditionTypeMap.containsKey(key)) return Unchecked.cast(conditionTypeMap.get(key));
      return null;
    }
  }
}
