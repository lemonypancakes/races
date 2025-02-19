package me.lemonypancakes.races.power;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import me.lemonypancakes.races.power.behavior.PowerBehavior;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public record Power(
    NamespacedKey key,
    String name,
    String displayName,
    String description,
    ItemStack icon,
    List<PowerBehavior<?>> behaviors) {
  public Power {
    Objects.requireNonNull(key, "key cannot be null");
    Objects.requireNonNull(name, "name cannot be null");
    Objects.requireNonNull(displayName, "displayName cannot be null");
    Objects.requireNonNull(description, "description cannot be null");
    Objects.requireNonNull(icon, "icon cannot be null");
    Objects.requireNonNull(behaviors, "behaviors cannot be null");
    behaviors = Collections.unmodifiableList(behaviors);
  }

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
