package me.lemonypancakes.races.player;

import java.util.*;
import me.lemonypancakes.races.RacesPlugin;
import me.lemonypancakes.races.power.PowerInstance;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

public final class PlayerManager implements Listener {
  private final RacesPlugin plugin;
  private final Map<UUID, Player> players;

  public PlayerManager(RacesPlugin plugin) {
    this.plugin = plugin;
    players = new HashMap<>();
  }

  public RacesPlugin getPlugin() {
    return plugin;
  }

  public Collection<Player> getPlayers() {
    return Collections.unmodifiableCollection(players.values());
  }

  public Player getPlayer(UUID uuid) {
    return players.get(uuid);
  }

  public Player getPlayer(org.bukkit.entity.Player player) {
    return getPlayer(player.getUniqueId());
  }

  @EventHandler
  private void onPlayerJoin(PlayerJoinEvent event) {
    org.bukkit.entity.Player player = event.getPlayer();
    UUID uuid = player.getUniqueId();
    Player racesPlayer = new Player(player, null);

    players.put(uuid, racesPlayer);
  }

  @EventHandler
  private void onPlayerQuit(PlayerQuitEvent event) {
    org.bukkit.entity.Player player = event.getPlayer();
    UUID uuid = player.getUniqueId();

    if (!players.containsKey(uuid)) return;
    Player racesPlayer = players.get(uuid);

    racesPlayer.getPowers().keySet().forEach(PowerInstance::remove);
    players.remove(uuid);
  }

  @EventHandler
  private void onPluginDisable(PluginDisableEvent event) {
    org.bukkit.plugin.Plugin plugin = event.getPlugin();

    if (!(plugin instanceof RacesPlugin)) return;
    players.forEach(((uuid, player) -> player.getPowers().keySet().forEach(PowerInstance::remove)));
    players.clear();
  }

  public void tick() {
    players.values().forEach(Player::tick);
  }
}
