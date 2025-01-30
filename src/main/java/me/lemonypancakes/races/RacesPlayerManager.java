package me.lemonypancakes.races;

import java.util.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class RacesPlayerManager implements Listener {
  private final Map<UUID, RacesPlayer> players;

  public RacesPlayerManager() {
    players = new HashMap<>();
    new BukkitRunnable() {
      @Override
      public void run() {
        tick();
      }
    }.runTaskTimer(Races.getPlugin(), 0L, 1L);
  }

  public Collection<RacesPlayer> getPlayers() {
    return Collections.unmodifiableCollection(players.values());
  }

  @EventHandler
  private void onPlayer(AsyncPlayerPreLoginEvent event) {}

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
    players.remove(uuid);
  }

  @EventHandler
  private void onPluginDisable(PluginDisableEvent event) {
    Plugin plugin = event.getPlugin();

    if (!(plugin instanceof RacesPlugin)) return;
    for (Map.Entry<UUID, RacesPlayer> entry : players.entrySet()) {}
  }

  private void tick() {
    players.values().forEach(RacesPlayer::tick);
  }
}
