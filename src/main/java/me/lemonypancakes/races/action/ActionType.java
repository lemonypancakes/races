package me.lemonypancakes.races.action;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import me.lemonypancakes.races.Races;
import me.lemonypancakes.races.serialization.DataSchema;
import me.lemonypancakes.races.util.Unchecked;
import org.bukkit.NamespacedKey;

public record ActionType<T>(Class<T> typeClass, NamespacedKey key, ActionFactory<T> factory) {
  public static <T> ActionType<T> register(
      final Class<T> typeClass, final String name, final ActionFactory<T> factory) {
    return register(typeClass, Races.namespace(name), factory);
  }

  public static <T> ActionType<T> registerSimple(
      final Class<T> typeClass,
      final String name,
      final DataSchema schema,
      final Consumer<T> action) {
    return registerSimple(typeClass, Races.namespace(name), schema, action);
  }

  public static <T> ActionType<T> registerSimple(
      final Class<T> typeClass,
      final NamespacedKey key,
      final DataSchema schema,
      final Consumer<T> action) {
    return register(
        typeClass,
        key,
        new ActionFactory<>(
            schema,
            container ->
                new Action<>() {
                  @Override
                  public void accept(final T t) {
                    action.accept(t);
                  }
                }));
  }

  public static <T> ActionType<T> register(
      final Class<T> typeClass, final NamespacedKey key, final ActionFactory<T> factory) {
    return register(new ActionType<>(typeClass, key, factory));
  }

  public static <T> ActionType<T> register(final ActionType<T> actionType) {
    return Registry.INSTANCE.register(actionType);
  }

  public static <T> ActionType<T> get(final Class<T> typeClass, final NamespacedKey key) {
    return Registry.INSTANCE.get(typeClass, key);
  }

  private enum Registry {
    INSTANCE;

    private final Map<Class<?>, Map<NamespacedKey, ActionType<?>>> registry;

    Registry() {
      this.registry = new HashMap<>();
    }

    public <T> ActionType<T> register(final ActionType<T> actionType) {
      final Class<T> typeClass = actionType.typeClass;
      if (!this.registry.containsKey(typeClass)) this.registry.put(typeClass, new HashMap<>());
      this.registry.get(typeClass).putIfAbsent(actionType.key, actionType);
      return actionType;
    }

    public <T> ActionType<T> get(final Class<T> typeClass, final NamespacedKey key) {
      if (!this.registry.containsKey(typeClass)) return null;
      final Map<NamespacedKey, ActionType<?>> actionTypeMap = this.registry.get(typeClass);
      if (actionTypeMap.containsKey(key)) return Unchecked.cast(actionTypeMap.get(key));
      return null;
    }
  }
}
