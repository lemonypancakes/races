package me.lemonypancakes.races.user;

import java.util.*;
import me.lemonypancakes.races.plugin.RacesPlugin;
import me.lemonypancakes.races.power.PowerInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

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

  public Collection<User> getUsers() {
    return Collections.unmodifiableCollection(users.values());
  }

  public User getUser(UUID uuid) {
    return users.get(uuid);
  }

  public User getUser(Player player) {
    return getUser(player.getUniqueId());
  }

  @EventHandler
  private void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    UUID uuid = player.getUniqueId();
    User user = new User(player, null);

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
