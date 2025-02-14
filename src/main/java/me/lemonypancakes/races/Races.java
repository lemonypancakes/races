package me.lemonypancakes.races;

import java.util.UUID;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

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

  public static RacesPlayer getPlayer(Player player) {
    return plugin.getPlayerManager().getPlayer(player);
  }

  public static RacesPlayer getPlayer(UUID uuid) {
    return plugin.getPlayerManager().getPlayer(uuid);
  }

  public static NamespacedKey namespace(String key) {
    return new NamespacedKey(plugin, key);
  }
}
