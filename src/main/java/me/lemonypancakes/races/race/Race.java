package me.lemonypancakes.races.race;

import java.util.Collections;
import java.util.List;
import me.lemonypancakes.races.power.Power;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public record Race(
    @NotNull String name,
    @NotNull String description,
    @NotNull ItemStack icon,
    @NotNull RaceImpact impact,
    @NotNull List<Power> powers,
    int order) {
  public Race {
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
