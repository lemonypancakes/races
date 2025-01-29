package me.lemonypancakes.races.action;

import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import me.lemonypancakes.races.Races;
import me.lemonypancakes.races.util.Unchecked;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;

public record ActionType<T>(Class<T> typeClass, NamespacedKey key, ActionFactory<T> factory) {
  public static final ActionType<Entity> TEST;

  static {
    TEST = register(Entity.class, "test", TestAction.FACTORY);
  }

  public static <T> ActionType<T> register(
      Class<T> typeClass, String name, ActionFactory<T> factory) {
    return register(typeClass, Races.namespace(name), factory);
  }

  public static <T> ActionType<T> registerSimple(
      Class<T> typeClass, String name, BiConsumer<JsonObject, T> action) {
    return registerSimple(typeClass, Races.namespace(name), action);
  }

  public static <T> ActionType<T> registerSimple(
      Class<T> typeClass, NamespacedKey key, BiConsumer<JsonObject, T> action) {
    return register(
        typeClass,
        key,
        data ->
            new Action<>() {
              @Override
              public void accept(T t) {
                action.accept(data, t);
              }
            });
  }

  public static <T> ActionType<T> register(
      Class<T> typeClass, NamespacedKey key, ActionFactory<T> factory) {
    return register(new ActionType<>(typeClass, key, factory));
  }

  public static <T> ActionType<T> register(ActionType<T> actionType) {
    return Registry.INSTANCE.register(actionType);
  }

  public static <T> ActionType<T> get(Class<T> typeClass, NamespacedKey key) {
    return Registry.INSTANCE.get(typeClass, key);
  }

  private enum Registry {
    INSTANCE;

    private final Map<Class<?>, Map<NamespacedKey, ActionType<?>>> registry;

    Registry() {
      registry = new HashMap<>();
    }

    public <T> ActionType<T> register(ActionType<T> actionType) {
      Class<T> typeClass = actionType.typeClass;
      if (!registry.containsKey(typeClass)) registry.put(typeClass, new HashMap<>());
      registry.get(typeClass).putIfAbsent(actionType.key, actionType);
      return actionType;
    }

    public <T> ActionType<T> get(Class<T> typeClass, NamespacedKey key) {
      if (!registry.containsKey(typeClass)) return null;
      Map<NamespacedKey, ActionType<?>> actionTypeMap = registry.get(typeClass);
      if (actionTypeMap.containsKey(key)) return Unchecked.cast(actionTypeMap.get(key));
      return null;
    }
  }
}
