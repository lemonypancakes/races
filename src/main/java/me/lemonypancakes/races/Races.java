package me.lemonypancakes.races;

import java.util.Objects;
import java.util.UUID;
import me.lemonypancakes.races.user.User;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Races {
  private static RacesPlugin plugin;

  private Races() {
    throw new UnsupportedOperationException("This class can not be instantiated.");
  }

  @NotNull
  public static RacesPlugin getPlugin() {
    if (plugin == null) throw new IllegalStateException("plugin is not set");
    return plugin;
  }

  public static void setPlugin(@NotNull RacesPlugin plugin) {
    Objects.requireNonNull(plugin, "plugin cannot be null");
    if (Races.plugin != null) throw new IllegalStateException("plugin is already set");
    Races.plugin = plugin;
  }

  @Nullable
  public static User getUser(@NotNull Player player) {
    return plugin.getUserManager().getUser(player);
  }

  @Nullable
  public static User getUser(@NotNull UUID uuid) {
    return plugin.getUserManager().getUser(uuid);
  }

  @NotNull
  public static NamespacedKey namespace(@NotNull String key) {
    return new NamespacedKey(plugin, key);
  }
}
