package me.lemonypancakes.races;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

public final class RacesPlayerManager implements Listener {
  private final Set<RacesPlayer> players;

  public RacesPlayerManager() {
    players = new HashSet<>();
  }

  public Collection<RacesPlayer> getPlayers() {
    return Collections.unmodifiableCollection(players);
  }

  @EventHandler
  private void onPlayer(AsyncPlayerPreLoginEvent event) {}

  @EventHandler
  private void onPlayerJoin(PlayerJoinEvent event) {
    players.add(new RacesPlayer(event.getPlayer()));
  }

  @EventHandler
  private void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
  }

  @EventHandler
  private void onPluginDisable(PluginDisableEvent event) {
    Plugin plugin = event.getPlugin();

    if (!(plugin instanceof RacesPlugin)) return;
    players.forEach(RacesPlayer::tick);
  }
}
