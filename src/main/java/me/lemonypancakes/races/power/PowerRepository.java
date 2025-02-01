package me.lemonypancakes.races.power;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import me.lemonypancakes.races.RacesPlugin;
import org.bukkit.NamespacedKey;

public final class PowerRepository {
  private final RacesPlugin plugin;
  private final Map<NamespacedKey, Power> powers;
  private boolean closed;

  public PowerRepository(RacesPlugin plugin) {
    this.plugin = plugin;
    powers = new HashMap<>();
  }

  public RacesPlugin getPlugin() {
    return plugin;
  }

  public Collection<Power> getPowers() {
    return Collections.unmodifiableCollection(powers.values());
  }

  public Power getPower(NamespacedKey key) {
    return powers.get(key);
  }

  public PowerRepository reload() {
    return this;
  }

  public void close() {
    if (closed) {
      throw new IllegalStateException("Already closed");
    }
    closed = true;
  }
}
