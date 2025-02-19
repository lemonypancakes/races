package me.lemonypancakes.races.power.behavior;

import java.util.Objects;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public record PowerBehaviorType<T extends PowerBehavior<T>>(
    @NotNull NamespacedKey key, @NotNull PowerBehaviorFactory<T> factory) {
  public PowerBehaviorType {
    Objects.requireNonNull(key, "key cannot be null");
    Objects.requireNonNull(factory, "factory cannot be null");
  }
}
