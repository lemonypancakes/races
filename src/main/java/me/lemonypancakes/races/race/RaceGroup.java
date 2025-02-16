package me.lemonypancakes.races.race;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public record RaceGroup(
    @NotNull NamespacedKey key, @NotNull String name, @NotNull List<Race> races) {
  public RaceGroup {
    Objects.requireNonNull(key, "key cannot be null");
    Objects.requireNonNull(name, "name cannot be null");
    Objects.requireNonNull(races, "races cannot be null");
  }

  @Override
  public List<Race> races() {
    return Collections.unmodifiableList(races);
  }
}
