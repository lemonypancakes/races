package me.lemonypancakes.races.race;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import me.lemonypancakes.races.RacesPlugin;
import org.bukkit.NamespacedKey;

public final class RaceRepository {
  private final RacesPlugin plugin;
  private final Map<NamespacedKey, Race> races;

  public RaceRepository(RacesPlugin plugin) {
    this.plugin = plugin;
    races = new HashMap<>();
  }

  public RacesPlugin getPlugin() {
    return plugin;
  }

  public Collection<Race> getRaces() {
    return Collections.unmodifiableCollection(races.values());
  }
}
