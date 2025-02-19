package me.lemonypancakes.races;

import java.util.UUID;
import me.lemonypancakes.races.plugin.RacesPlugin;
import me.lemonypancakes.races.user.User;
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

  public static User getPlayer(org.bukkit.entity.Player player) {
    return plugin.getUserManager().getPlayer(player);
  }

  public static User getPlayer(UUID uuid) {
    return plugin.getUserManager().getPlayer(uuid);
  }

  public static NamespacedKey namespace(String key) {
    return new NamespacedKey(plugin, key);
  }
}
