package me.lemonypancakes.races.condition;

import java.util.function.BiPredicate;
import me.lemonypancakes.races.Races;
import me.lemonypancakes.races.registry.Registries;
import me.lemonypancakes.races.serialization.DataContainer;
import me.lemonypancakes.races.serialization.DataSchema;
import me.lemonypancakes.races.util.TypedNamespacedKey;
import me.lemonypancakes.races.util.Unchecked;
import org.bukkit.NamespacedKey;

public final class ConditionTypes {
  public static <T> ConditionType<T> register(ConditionType<T> type) {
    return Unchecked.cast(
        Registries.CONDITION_TYPE.register(
            new TypedNamespacedKey<>(type.typeClass(), type.key()), type));
  }

  public static <T> ConditionType<T> register(
      Class<T> typeClass, NamespacedKey key, ConditionFactory<T> factory) {
    return register(new ConditionType<>(typeClass, key, factory));
  }

  public static <T> ConditionType<T> registerSimple(
      Class<T> typeClass,
      NamespacedKey key,
      DataSchema schema,
      BiPredicate<DataContainer, T> condition) {
    return register(
        typeClass,
        key,
        new ConditionFactory<>(
            schema,
            container ->
                new Condition<>() {
                  @Override
                  public boolean test(T t) {
                    return condition.test(container, t);
                  }
                }));
  }

  public static <T> ConditionType<T> get(Class<T> typeClass, NamespacedKey key) {
    return Unchecked.cast(Registries.CONDITION_TYPE.get(new TypedNamespacedKey<>(typeClass, key)));
  }

  private static <T> ConditionType<T> register(
      Class<T> typeClass, String name, ConditionFactory<T> factory) {
    return register(typeClass, Races.namespace(name), factory);
  }

  private static <T> ConditionType<T> registerSimple(
      Class<T> typeClass, String name, DataSchema schema, BiPredicate<DataContainer, T> condition) {
    return registerSimple(typeClass, Races.namespace(name), schema, condition);
  }

  private ConditionTypes() {}
}
