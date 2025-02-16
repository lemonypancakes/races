package me.lemonypancakes.races;

import me.lemonypancakes.races.player.Player;
import me.lemonypancakes.races.plugin.Plugin;
import org.bukkit.NamespacedKey;

import java.util.UUID;

public final class Races {
  private static Plugin plugin;

  private Races() {
    throw new UnsupportedOperationException();
  }

  public static Plugin getPlugin() {
    return plugin;
  }

  public static void setPlugin(Plugin plugin) {
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
