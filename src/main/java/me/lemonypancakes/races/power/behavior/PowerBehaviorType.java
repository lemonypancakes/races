package me.lemonypancakes.races.power.behavior;

import java.util.HashMap;
import java.util.Map;
import me.lemonypancakes.races.Races;
import me.lemonypancakes.races.util.Unchecked;
import org.bukkit.NamespacedKey;

public record PowerBehaviorType<T extends PowerBehavior<T>>(
    NamespacedKey key, PowerBehaviorFactory<T> factory) {
  public static final PowerBehaviorType<AttributePowerBehavior> ATTRIBUTE;
  public static final PowerBehaviorType<OverTimePowerBehavior> OVER_TIME;

  static {
    ATTRIBUTE = register("attribute", AttributePowerBehavior.FACTORY);
    OVER_TIME = register("over_time", OverTimePowerBehavior.FACTORY);
  }

  public static <T extends PowerBehavior<T>> PowerBehaviorType<T> register(
      final String name, final PowerBehaviorFactory<T> factory) {
    return register(Races.namespace(name), factory);
  }

  public static <T extends PowerBehavior<T>> PowerBehaviorType<T> register(
      final NamespacedKey key, final PowerBehaviorFactory<T> factory) {
    return Registry.INSTANCE.register(new PowerBehaviorType<>(key, factory));
  }

  public static <T extends PowerBehavior<T>> PowerBehaviorType<T> get(final NamespacedKey key) {
    return Registry.INSTANCE.get(key);
  }

  private enum Registry {
    INSTANCE;

    private final Map<NamespacedKey, PowerBehaviorType<?>> registry;

    Registry() {
      this.registry = new HashMap<>();
    }

    public <T extends PowerBehavior<T>> PowerBehaviorType<T> register(
        final PowerBehaviorType<T> powerBehaviorType) {
      return Unchecked.cast(this.registry.putIfAbsent(powerBehaviorType.key(), powerBehaviorType));
    }

    public <T extends PowerBehavior<T>> PowerBehaviorType<T> get(final NamespacedKey key) {
      return Unchecked.cast(this.registry.get(key));
    }
  }
}
