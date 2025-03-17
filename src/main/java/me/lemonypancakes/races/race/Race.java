package me.lemonypancakes.races.race;

import me.lemonypancakes.races.power.Power;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public record Race(
    @NotNull NamespacedKey key,
    @NotNull String name,
    @NotNull String description,
    @NotNull ItemStack icon,
    @NotNull RaceImpact impact,
    @NotNull List<Power> powers,
    int order) {
  public Race {
    Objects.requireNonNull(key, "Key cannot be null");
    Objects.requireNonNull(name, "name cannot be null");
    Objects.requireNonNull(description, "description cannot be null");
    Objects.requireNonNull(icon, "icon cannot be null");
    Objects.requireNonNull(impact, "impact cannot be null");
    Objects.requireNonNull(powers, "powers cannot be null");
    powers = List.copyOf(powers);
    if (order < 0) throw new IllegalArgumentException("order cannot be negative");
  }

  @Override
  public ItemStack icon() {
    return icon.clone();
  }
}
