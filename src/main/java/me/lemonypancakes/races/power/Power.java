package me.lemonypancakes.races.power;

import me.lemonypancakes.races.power.behavior.PowerBehavior;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public record Power(
    NamespacedKey key,
    String name,
    String displayName,
    String description,
    ItemStack icon,
    int impact,
    int order,
    PowerBehavior<?> behavior) {

  @Override
  public boolean equals(final Object object) {
    if (this == object) return true;
    if (!(object instanceof final Power power)) return false;

    return this.key.equals(power.key);
  }

  @Override
  public int hashCode() {
    return this.key.hashCode();
  }

  @Override
  public String toString() {
    return this.key.toString();
  }
}
