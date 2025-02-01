package me.lemonypancakes.races;

import java.util.*;
import me.lemonypancakes.races.power.PowerInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

public final class RacesPlayerManager implements Listener {
  private final Map<UUID, RacesPlayer> players;

  public RacesPlayerManager() {
    players = new HashMap<>();
  }

  public Collection<RacesPlayer> getPlayers() {
    return Collections.unmodifiableCollection(players.values());
  }

  public RacesPlayer getPlayer(UUID uuid) {
    return players.get(uuid);
  }

  public RacesPlayer getPlayer(Player player) {
    return getPlayer(player.getUniqueId());
  }

  @EventHandler
  private void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    UUID uuid = player.getUniqueId();
    RacesPlayer racesPlayer = new RacesPlayer(player);

    players.put(uuid, racesPlayer);
  }

  @EventHandler
  private void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    UUID uuid = player.getUniqueId();

    if (!players.containsKey(uuid)) return;
    RacesPlayer racesPlayer = players.get(uuid);

    racesPlayer.getPowers().forEach(PowerInstance::remove);
    players.remove(uuid);
  }

  @EventHandler
  private void onPluginDisable(PluginDisableEvent event) {
    Plugin plugin = event.getPlugin();

    if (!(plugin instanceof RacesPlugin)) return;
    players.forEach(
        ((uuid, racesPlayer) -> racesPlayer.getPowers().forEach(PowerInstance::remove)));
    players.clear();
  }

  public void tick() {
    players.values().forEach(RacesPlayer::tick);
  }
}
