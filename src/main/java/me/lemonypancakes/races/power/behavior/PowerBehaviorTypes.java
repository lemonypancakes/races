package me.lemonypancakes.races.power.behavior;

import me.lemonypancakes.races.Races;
import me.lemonypancakes.races.registry.Registries;
import me.lemonypancakes.races.util.Unchecked;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class PowerBehaviorTypes {
  public static final PowerBehaviorType<AttributePowerBehavior> ATTRIBUTE;
  public static final PowerBehaviorType<OverTimePowerBehavior> OVER_TIME;

  static {
    ATTRIBUTE = register("attribute", AttributePowerBehavior.FACTORY);
    OVER_TIME = register("over_time", OverTimePowerBehavior.FACTORY);
  }

  @Nullable
  public static PowerBehaviorType<?> get(@NotNull NamespacedKey key) {
    Objects.requireNonNull(key, "key cannot be null");
    return Registries.POWER_BEHAVIOR_TYPE.get(key);
  }

  @NotNull
  public static <T extends PowerBehavior<T>> PowerBehaviorType<T> register(
      @NotNull NamespacedKey key, @NotNull PowerBehaviorFactory<T> factory) {
    Objects.requireNonNull(key, "key cannot be null");
    Objects.requireNonNull(factory, "factory cannot be null");
    return Unchecked.cast(
        Registries.POWER_BEHAVIOR_TYPE.register(key, new PowerBehaviorType<>(key, factory)));
  }

  @NotNull
  private static <T extends PowerBehavior<T>> PowerBehaviorType<T> register(
      @NotNull String name, @NotNull PowerBehaviorFactory<T> factory) {
    return register(Races.namespace(name), factory);
  }

  private PowerBehaviorTypes() {
    throw new UnsupportedOperationException("This class cannot be instantiated.");
  }
}
