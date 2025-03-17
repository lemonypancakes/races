package me.lemonypancakes.races.condition;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record ConditionType<T>(
    @NotNull Class<T> typeClass, @NotNull NamespacedKey key, @NotNull ConditionFactory<T> factory) {
  public ConditionType {
    Objects.requireNonNull(typeClass, "typeClass cannot be null");
    Objects.requireNonNull(key, "key cannot be null");
    Objects.requireNonNull(factory, "factory cannot be null");
  }
}
