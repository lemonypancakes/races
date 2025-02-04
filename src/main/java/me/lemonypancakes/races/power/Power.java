package me.lemonypancakes.races.power;

import me.lemonypancakes.races.power.behavior.PowerBehavior;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record Power(
    @NotNull NamespacedKey key,
    @NotNull String name,
    @NotNull String displayName,
    @NotNull String description,
    @NotNull ItemStack icon,
    @Nullable PowerBehavior<?> behavior) {

  @Override
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
  public String toString() {
    return key.toString();
  }
}
