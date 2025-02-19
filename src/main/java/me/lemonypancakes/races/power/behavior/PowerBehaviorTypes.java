package me.lemonypancakes.races.power.behavior;

import me.lemonypancakes.races.Races;
import me.lemonypancakes.races.registry.Registries;
import me.lemonypancakes.races.util.Unchecked;
import org.bukkit.NamespacedKey;

public final class PowerBehaviorTypes {
  public static final PowerBehaviorType<AttributePowerBehavior> ATTRIBUTE;
  public static final PowerBehaviorType<OverTimePowerBehavior> OVER_TIME;

  static {
    ATTRIBUTE = register("attribute", AttributePowerBehavior.FACTORY);
    OVER_TIME = register("over_time", OverTimePowerBehavior.FACTORY);
  }

  public static <T extends PowerBehavior<T>> PowerBehaviorType<T> register(
      NamespacedKey key, PowerBehaviorFactory<T> factory) {
    return Unchecked.cast(
        Registries.POWER_BEHAVIOR_TYPE.register(key, new PowerBehaviorType<>(key, factory)));
  }

  public static PowerBehaviorType<?> get(NamespacedKey key) {
    return Registries.POWER_BEHAVIOR_TYPE.get(key);
  }

  private static <T extends PowerBehavior<T>> PowerBehaviorType<T> register(
      String name, PowerBehaviorFactory<T> factory) {
    return register(Races.namespace(name), factory);
  }

  private PowerBehaviorTypes() {
    throw new UnsupportedOperationException("This class cannot be instantiated.");
  }
}
