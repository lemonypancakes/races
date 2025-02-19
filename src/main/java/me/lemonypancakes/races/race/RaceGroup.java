package me.lemonypancakes.races.race;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.bukkit.NamespacedKey;

public record RaceGroup(NamespacedKey key, String name, List<Race> races) {
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
