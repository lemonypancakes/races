package me.lemonypancakes.races.power;

import me.lemonypancakes.races.power.behavior.PowerBehavior;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public record Power(
    @NotNull NamespacedKey key,
    @NotNull String name,
    @NotNull String displayName,
    @NotNull String description,
    @NotNull ItemStack icon,
    @NotNull List<PowerBehavior<?>> behaviors) {
  public Power {
    Objects.requireNonNull(key, "key cannot be null");
    Objects.requireNonNull(name, "name cannot be null");
    Objects.requireNonNull(displayName, "displayName cannot be null");
    Objects.requireNonNull(description, "description cannot be null");
    Objects.requireNonNull(icon, "icon cannot be null");
    Objects.requireNonNull(behaviors, "behaviors cannot be null");
    behaviors = List.copyOf(behaviors);
  }

  @Override
  @NotNull
  public ItemStack icon() {
    return icon.clone();
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (!(object instanceof Power power)) return false;

    return key.equals(power.key);
  }

  @Override
  public int hashCode() {
    return key.hashCode();
  }

  @Override
  @NotNull
  public String toString() {
    return key.toString();
  }
}
