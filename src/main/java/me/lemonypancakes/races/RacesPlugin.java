package me.lemonypancakes.races;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import java.util.ArrayList;
import java.util.List;
import me.lemonypancakes.races.power.Power;
import me.lemonypancakes.races.power.PowerInstance;
import me.lemonypancakes.races.power.PowerRepository;
import me.lemonypancakes.races.power.behavior.OverTimePowerBehavior;
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
    return this.players;
  }

  public PowerRepository getPowerRepository() {
    return this.powerRepository;
  }

  public RaceRepository getRaceRepository() {
    return this.raceRepository;
  }

  public void onLoad() {
    Races.setPlugin(this);
    this.players = new ArrayList<>();
    this.powerRepository = new PowerRepository().reload();
    this.raceRepository = new RaceRepository();
    CommandAPI.onLoad(
        new CommandAPIBukkitConfig(this)
            .setNamespace("races")
            .skipReloadDatapacks(true)
            .silentLogs(true));
  }

  public void onEnable() {
    CommandAPI.onEnable();
    new BukkitRunnable() {
      public void run() {
        RacesPlugin.this.players.forEach(RacesPlayer::tick);
      }
    }.runTaskTimer(this, 0L, 1L);
    Bukkit.getPluginManager()
        .registerEvents(
            new Listener() {
              @EventHandler
              public void onPlayerJoin(final PlayerJoinEvent event) {
                final RacesPlayer player = new RacesPlayer(event.getPlayer());
                final Power power =
                    new Power(
                        null,
                        null,
                        null,
                        null,
                        null,
                        0,
                        0,
                        new OverTimePowerBehavior(null, null, 1));
                final PowerInstance powerInstance = new PowerInstance(power, player.getHandle());
                player.addPower(powerInstance);
                powerInstance.grant();
                RacesPlugin.this.players.add(player);
              }

              @EventHandler
              public void onPlayerQuit(final PlayerQuitEvent event) {
                RacesPlugin.this.players.removeIf(
                    player ->
                        player.getHandle().getUniqueId().equals(event.getPlayer().getUniqueId()));
              }
            },
            this);
  }

  public void onDisable() {}
}
