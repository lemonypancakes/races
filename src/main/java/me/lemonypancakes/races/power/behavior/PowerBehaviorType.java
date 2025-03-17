package me.lemonypancakes.races.power.behavior;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record PowerBehaviorType<T extends PowerBehavior<T>>(
    @NotNull NamespacedKey key, @NotNull PowerBehaviorFactory<T> factory) {
  public PowerBehaviorType {
    Objects.requireNonNull(key, "key cannot be null");
    Objects.requireNonNull(factory, "factory cannot be null");
  }
}
