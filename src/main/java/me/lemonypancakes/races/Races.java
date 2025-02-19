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

  public static User getUser(org.bukkit.entity.Player player) {
    return plugin.getUserManager().getUser(player);
  }

  public static User getUser(UUID uuid) {
    return plugin.getUserManager().getUser(uuid);
  }

  public static NamespacedKey namespace(String key) {
    return new NamespacedKey(plugin, key);
  }
}
