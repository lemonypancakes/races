package me.lemonypancakes.races;

import org.bukkit.NamespacedKey;

public final class Races {
  private static RacesPlugin plugin;

  private Races() {
    throw new UnsupportedOperationException();
  }

  public static RacesPlugin getPlugin() {
    return plugin;
  }

  public static void setPlugin(RacesPlugin plugin) {
    if (plugin == null) throw new NullPointerException();
    if (Races.plugin != null) throw new RuntimeException();
    Races.plugin = plugin;
  }

  public static NamespacedKey namespace(String key) {
    return new NamespacedKey(plugin, key);
  }
}
