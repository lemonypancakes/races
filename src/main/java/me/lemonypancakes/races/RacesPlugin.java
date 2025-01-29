package me.lemonypancakes.races;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import java.util.ArrayList;
import java.util.List;
import me.lemonypancakes.races.power.Power;
import me.lemonypancakes.races.power.PowerInstance;
import me.lemonypancakes.races.power.PowerRepository;
import me.lemonypancakes.races.race.RaceRepository;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class RacesPlugin extends JavaPlugin {
  private List<RacesPlayer> players;
  private PowerRepository powerRepository;
  private RaceRepository raceRepository;

  public List<RacesPlayer> getPlayers() {
    return players;
  }

  public PowerRepository getPowerRepository() {
    return powerRepository;
  }

  public RaceRepository getRaceRepository() {
    return raceRepository;
  }

  public void onLoad() {
    Races.setPlugin(this);
    players = new ArrayList<>();
    powerRepository = new PowerRepository().reload();
    raceRepository = new RaceRepository();
    CommandAPI.onLoad(new CommandAPIBukkitConfig(this).setNamespace("races"));
  }

  public void onEnable() {
    CommandAPI.onEnable();
    new BukkitRunnable() {
      public void run() {
        players.forEach(RacesPlayer::tick);
      }
    }.runTaskTimer(this, 0L, 1L);
    Bukkit.getPluginManager()
        .registerEvents(
            new Listener() {
              @EventHandler
              public void onPlayerJoin(PlayerJoinEvent event) {
                RacesPlayer player = new RacesPlayer(event.getPlayer());
                Power power = Power.EMPTY;
                PowerInstance powerInstance = new PowerInstance(power, player.getHandle());
                player.addPower(powerInstance);
                powerInstance.grant();
                players.add(player);
              }

              @EventHandler
              public void onPlayerQuit(PlayerQuitEvent event) {
                players.removeIf(player -> player.getHandle() == event.getPlayer());
              }
            },
            this);
  }

  public void onDisable() {}
}
