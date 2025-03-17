package me.lemonypancakes.races.user;

import me.lemonypancakes.races.RacesPlugin;
import me.lemonypancakes.races.power.PowerInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class UserManager implements Listener {
  private final RacesPlugin plugin;
  private final Map<UUID, User> users;

  public UserManager(RacesPlugin plugin) {
    this.plugin = plugin;
    users = new HashMap<>();
  }

  public RacesPlugin getPlugin() {
    return plugin;
  }

  @NotNull
  public Collection<User> getUsers() {
    return Collections.unmodifiableCollection(users.values());
  }

  @Nullable
  public User getUser(UUID uuid) {
    return users.get(uuid);
  }

  @Nullable
  public User getUser(Player player) {
    return getUser(player.getUniqueId());
  }

  @EventHandler
  private void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    UUID uuid = player.getUniqueId();
    User user =
        new User(
            player, new UserData(player.getUniqueId(), new HashMap<>(), new HashMap<>(), false));

    users.put(uuid, user);
  }

  @EventHandler
  private void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    UUID uuid = player.getUniqueId();

    if (!users.containsKey(uuid)) return;
    User user = users.get(uuid);

    user.getPowers().keySet().forEach(PowerInstance::remove);
    users.remove(uuid);
  }

  @EventHandler
  private void onPluginDisable(PluginDisableEvent event) {
    org.bukkit.plugin.Plugin plugin = event.getPlugin();

    if (!(plugin instanceof RacesPlugin)) return;
    users.forEach(((uuid, user) -> user.getPowers().keySet().forEach(PowerInstance::remove)));
    users.clear();
  }

  public void tick() {
    users.values().forEach(User::tick);
  }
}
