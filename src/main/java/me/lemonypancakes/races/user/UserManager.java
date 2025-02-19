package me.lemonypancakes.races.user;

import java.util.*;
import me.lemonypancakes.races.plugin.RacesPlugin;
import me.lemonypancakes.races.power.PowerInstance;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

public final class UserManager implements Listener {
  private final RacesPlugin plugin;
  private final Map<UUID, User> players;

  public UserManager(RacesPlugin plugin) {
    this.plugin = plugin;
    players = new HashMap<>();
  }

  public RacesPlugin getPlugin() {
    return plugin;
  }

  public Collection<User> getPlayers() {
    return Collections.unmodifiableCollection(players.values());
  }

  public User getPlayer(UUID uuid) {
    return players.get(uuid);
  }

  public User getPlayer(org.bukkit.entity.Player player) {
    return getPlayer(player.getUniqueId());
  }

  @EventHandler
  private void onPlayerJoin(PlayerJoinEvent event) {
    org.bukkit.entity.Player player = event.getPlayer();
    UUID uuid = player.getUniqueId();
    User racesUser = new User(player, null);

    players.put(uuid, racesUser);
  }

  @EventHandler
  private void onPlayerQuit(PlayerQuitEvent event) {
    org.bukkit.entity.Player player = event.getPlayer();
    UUID uuid = player.getUniqueId();

    if (!players.containsKey(uuid)) return;
    User racesUser = players.get(uuid);

    racesUser.getPowers().keySet().forEach(PowerInstance::remove);
    players.remove(uuid);
  }

  @EventHandler
  private void onPluginDisable(PluginDisableEvent event) {
    org.bukkit.plugin.Plugin plugin = event.getPlugin();

    if (!(plugin instanceof RacesPlugin)) return;
    players.forEach(((uuid, user) -> user.getPowers().keySet().forEach(PowerInstance::remove)));
    players.clear();
  }

  public void tick() {
    players.values().forEach(User::tick);
  }
}
