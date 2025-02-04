package me.lemonypancakes.races.registry;

import java.util.HashMap;
import java.util.Map;
import me.lemonypancakes.races.action.ActionType;
import me.lemonypancakes.races.condition.ConditionType;
import me.lemonypancakes.races.power.behavior.PowerBehaviorType;
import me.lemonypancakes.races.util.TypedNamespacedKey;
import org.bukkit.NamespacedKey;

public final class Registry<K, V> {
  public static final Registry<TypedNamespacedKey<?>, ActionType<?>> ACTION_TYPE;
  public static final Registry<TypedNamespacedKey<?>, ConditionType<?>> CONDITION_TYPE;
  public static final Registry<NamespacedKey, PowerBehaviorType<?>> POWER_BEHAVIOR_TYPE;

  static {
    ACTION_TYPE = new Registry<>(new HashMap<>());
    CONDITION_TYPE = new Registry<>(new HashMap<>());
    POWER_BEHAVIOR_TYPE = new Registry<>(new HashMap<>());
  }

  private final Map<K, V> registry;

  public Registry(Map<K, V> registry) {
    this.registry = registry;
  }

  public V register(K key, V value) {
    return registry.putIfAbsent(key, value);
  }

  public V get(K key) {
    return registry.get(key);
  }

  public boolean has(K key) {
    return registry.containsKey(key);
  }
}
