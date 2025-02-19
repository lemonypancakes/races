package me.lemonypancakes.races.race;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import me.lemonypancakes.races.power.Power;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public record Race(
    NamespacedKey key,
    String name,
    String description,
    ItemStack icon,
    RaceImpact impact,
    List<Power> powers,
    int order) {
  public Race {
    Objects.requireNonNull(key, "Key cannot be null");
    Objects.requireNonNull(name, "name cannot be null");
    Objects.requireNonNull(description, "description cannot be null");
    Objects.requireNonNull(icon, "icon cannot be null");
    Objects.requireNonNull(impact, "impact cannot be null");
    Objects.requireNonNull(powers, "powers cannot be null");
    if (order < 0) {
      throw new IllegalArgumentException("Order cannot be negative");
    }
  }

  @Override
  public ItemStack icon() {
    return icon.clone();
  }

  @Override
  public List<Power> powers() {
    return Collections.unmodifiableList(powers);
  }
}
