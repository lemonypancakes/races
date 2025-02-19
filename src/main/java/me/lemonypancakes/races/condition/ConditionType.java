package me.lemonypancakes.races.condition;

import java.util.Objects;
import org.bukkit.NamespacedKey;

public record ConditionType<T>(Class<T> typeClass, NamespacedKey key, ConditionFactory<T> factory) {
  public ConditionType {
    Objects.requireNonNull(typeClass, "typeClass cannot be null");
    Objects.requireNonNull(key, "key cannot be null");
    Objects.requireNonNull(factory, "factory cannot be null");
  }
}
