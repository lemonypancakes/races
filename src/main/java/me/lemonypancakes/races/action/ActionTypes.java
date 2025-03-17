package me.lemonypancakes.races.action;

import me.lemonypancakes.races.Races;
import me.lemonypancakes.races.registry.Registries;
import me.lemonypancakes.races.serialization.DataContainer;
import me.lemonypancakes.races.serialization.DataSchema;
import me.lemonypancakes.races.util.TypedNamespacedKey;
import me.lemonypancakes.races.util.Unchecked;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiConsumer;

public final class ActionTypes {
  @Nullable
  public static <T> ActionType<T> get(@NotNull Class<T> typeClass, @NotNull NamespacedKey key) {
    Objects.requireNonNull(typeClass, "typeClass cannot be null");
    Objects.requireNonNull(key, "key cannot be null");
    return Unchecked.cast(Registries.ACTION_TYPE.get(new TypedNamespacedKey<>(typeClass, key)));
  }

  @NotNull
  public static <T> ActionType<T> registerSimple(
      @NotNull Class<T> typeClass,
      @NotNull NamespacedKey key,
      @NotNull DataSchema schema,
      @NotNull BiConsumer<DataContainer, T> action) {
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
                  protected void onAccept(@NotNull T t) {
                    action.accept(container, t);
                  }
                }));
  }

  @NotNull
  public static <T> ActionType<T> register(
      @NotNull Class<T> typeClass, @NotNull NamespacedKey key, @NotNull ActionFactory<T> factory) {
    Objects.requireNonNull(typeClass, "typeClass cannot be null");
    Objects.requireNonNull(key, "key cannot be null");
    Objects.requireNonNull(factory, "factory cannot be null");
    return register(new ActionType<>(typeClass, key, factory));
  }

  @NotNull
  public static <T> ActionType<T> register(@NotNull ActionType<T> type) {
    Objects.requireNonNull(type, "type cannot be null");
    return Unchecked.cast(
        Registries.ACTION_TYPE.register(
            new TypedNamespacedKey<>(type.typeClass(), type.key()), type));
  }

  private static <T> ActionType<T> register(
      Class<T> typeClass, String name, ActionFactory<T> factory) {
    return register(typeClass, Races.namespace(name), factory);
  }

  private ActionTypes() {
    throw new UnsupportedOperationException("This class cannot be instantiated.");
  }
}
