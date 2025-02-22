package me.lemonypancakes.races.action;

import java.util.Objects;
import java.util.function.BiConsumer;
import me.lemonypancakes.races.Races;
import me.lemonypancakes.races.registry.Registries;
import me.lemonypancakes.races.serialization.DataContainer;
import me.lemonypancakes.races.serialization.DataSchema;
import me.lemonypancakes.races.util.TypedNamespacedKey;
import me.lemonypancakes.races.util.Unchecked;
import org.bukkit.NamespacedKey;

public final class ActionTypes {
  public static <T> ActionType<T> register(ActionType<T> type) {
    Objects.requireNonNull(type, "type cannot be null");
    return Unchecked.cast(
        Registries.ACTION_TYPE.register(
            new TypedNamespacedKey<>(type.typeClass(), type.key()), type));
  }

  public static <T> ActionType<T> register(
      Class<T> typeClass, NamespacedKey key, ActionFactory<T> factory) {
    Objects.requireNonNull(typeClass, "typeClass cannot be null");
    Objects.requireNonNull(key, "key cannot be null");
    Objects.requireNonNull(factory, "factory cannot be null");
    return register(new ActionType<>(typeClass, key, factory));
  }

  public static <T> ActionType<T> registerSimple(
      Class<T> typeClass,
      NamespacedKey key,
      DataSchema schema,
      BiConsumer<DataContainer, T> action) {
    Objects.requireNonNull(typeClass, "typeClass cannot be null");
    Objects.requireNonNull(key, "key cannot be null");
    Objects.requireNonNull(schema, "schema cannot be null");
    Objects.requireNonNull(action, "action cannot be null");
    return register(
        typeClass,
        key,
        new ActionFactory<>(
            schema,
            container ->
                new Action<>() {
                  @Override
                  public void accept(T t) {
                    action.accept(container, t);
                  }
                }));
  }

  public static <T> ActionType<T> get(Class<T> typeClass, NamespacedKey key) {
    Objects.requireNonNull(typeClass, "typeClass cannot be null");
    Objects.requireNonNull(key, "key cannot be null");
    return Unchecked.cast(Registries.ACTION_TYPE.get(new TypedNamespacedKey<>(typeClass, key)));
  }

  private static <T> ActionType<T> register(
      Class<T> typeClass, String name, ActionFactory<T> factory) {
    return register(typeClass, Races.namespace(name), factory);
  }

  private static <T> ActionType<T> registerSimple(
      Class<T> typeClass, String name, DataSchema schema, BiConsumer<DataContainer, T> action) {
    return registerSimple(typeClass, Races.namespace(name), schema, action);
  }

  private ActionTypes() {
    throw new UnsupportedOperationException("This class cannot be instantiated.");
  }
}
