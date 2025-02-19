package me.lemonypancakes.races.registry;

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
    STORAGE_TYPE = new Registry<>();
    ACTION_TYPE = new Registry<>();
    CONDITION_TYPE = new Registry<>();
    POWER_BEHAVIOR_TYPE = new Registry<>();
  }

  private Registries() {
    throw new UnsupportedOperationException("This class cannot be instantiated.");
  }
}
