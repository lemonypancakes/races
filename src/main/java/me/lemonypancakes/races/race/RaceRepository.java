package me.lemonypancakes.races.race;

import org.bukkit.NamespacedKey;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class RaceRepository {
  private final Map<NamespacedKey, Race> races;
  private final Map<NamespacedKey, RaceGroup> groups;

  public RaceRepository() {
    races = new HashMap<>();
    groups = new HashMap<>();
  }

  public Collection<Race> getRaces() {
    return Collections.unmodifiableCollection(races.values());
  }

  public Collection<RaceGroup> getGroups() {
    return Collections.unmodifiableCollection(groups.values());
  }
}
