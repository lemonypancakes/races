package me.lemonypancakes.races.registry;

import java.util.concurrent.ConcurrentHashMap;
import me.lemonypancakes.races.action.ActionType;
import me.lemonypancakes.races.condition.ConditionType;
import me.lemonypancakes.races.power.behavior.PowerBehaviorType;
import me.lemonypancakes.races.storage.StorageType;
import me.lemonypancakes.races.util.TypedNamespacedKey;
import org.bukkit.NamespacedKey;

public final class Registries {
  public static final Registry<String, StorageType> STORAGE_TYPE;
  public static final Registry<TypedNamespacedKey<?>, ActionType<?>> ACTION_TYPE;
  public static final Registry<TypedNamespacedKey<?>, ConditionType<?>> CONDITION_TYPE;
  public static final Registry<NamespacedKey, PowerBehaviorType<?>> POWER_BEHAVIOR_TYPE;

  static {
    STORAGE_TYPE = new Registry<>(new ConcurrentHashMap<>());
    ACTION_TYPE = new Registry<>(new ConcurrentHashMap<>());
    CONDITION_TYPE = new Registry<>(new ConcurrentHashMap<>());
    POWER_BEHAVIOR_TYPE = new Registry<>(new ConcurrentHashMap<>());
  }

  private Registries() {}
}
