package me.lemonypancakes.races.power.behavior;

import me.lemonypancakes.races.Races;
import me.lemonypancakes.races.registry.Registries;
import me.lemonypancakes.races.util.Unchecked;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PowerBehaviorTypes {
  public static final PowerBehaviorType<AttributePowerBehavior> ATTRIBUTE;
  public static final PowerBehaviorType<OverTimePowerBehavior> OVER_TIME;

  static {
    ATTRIBUTE = register("attribute", AttributePowerBehavior.FACTORY);
    OVER_TIME = register("over_time", OverTimePowerBehavior.FACTORY);
  }

  @NotNull
  public static <T extends PowerBehavior<T>> PowerBehaviorType<T> register(
      @NotNull NamespacedKey key, @NotNull PowerBehaviorFactory<T> factory) {
    return Unchecked.cast(
        Registries.POWER_BEHAVIOR_TYPE.register(key, new PowerBehaviorType<>(key, factory)));
  }

  @Nullable
  public static PowerBehaviorType<?> get(@NotNull NamespacedKey key) {
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
