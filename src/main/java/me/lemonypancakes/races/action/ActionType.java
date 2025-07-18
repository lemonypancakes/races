package me.lemonypancakes.races.action;

import java.util.Objects;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public record ActionType<T>(
    @NotNull Class<T> typeClass, @NotNull NamespacedKey key, @NotNull ActionFactory<T> factory) {
  public ActionType {
    Objects.requireNonNull(typeClass, "typeClass cannot be null");
    Objects.requireNonNull(key, "key cannot be null");
    Objects.requireNonNull(factory, "factory cannot be null");
  }
}
