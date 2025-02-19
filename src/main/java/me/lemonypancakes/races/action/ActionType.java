package me.lemonypancakes.races.action;

import java.util.Objects;
import org.bukkit.NamespacedKey;

public record ActionType<T>(Class<T> typeClass, NamespacedKey key, ActionFactory<T> factory) {
  public ActionType {
    Objects.requireNonNull(typeClass, "typeClass cannot be null");
    Objects.requireNonNull(key, "key cannot be null");
    Objects.requireNonNull(factory, "factory cannot be null");
  }
}
