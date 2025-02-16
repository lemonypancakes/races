package me.lemonypancakes.races;

import java.util.UUID;
import me.lemonypancakes.races.player.Player;
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

  public static Player getPlayer(org.bukkit.entity.Player player) {
    return plugin.getPlayerManager().getPlayer(player);
  }

  public static Player getPlayer(UUID uuid) {
    return plugin.getPlayerManager().getPlayer(uuid);
  }

  public static NamespacedKey namespace(String key) {
    return new NamespacedKey(plugin, key);
  }
}
