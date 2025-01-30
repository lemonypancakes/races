package me.lemonypancakes.races;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import me.lemonypancakes.races.power.PowerRepository;
import me.lemonypancakes.races.race.RaceRepository;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class RacesPlugin extends JavaPlugin {
  private RacesPlayerManager playerManager;
  private PowerRepository powerRepository;
  private RaceRepository raceRepository;

  public RacesPlayerManager getPlayerManager() {
    return playerManager;
  }

  public PowerRepository getPowerRepository() {
    return powerRepository;
  }

  public RaceRepository getRaceRepository() {
    return raceRepository;
  }

  @Override
  public void onLoad() {
    Races.setPlugin(this);
    playerManager = registerListener(new RacesPlayerManager());
    powerRepository = new PowerRepository().reload();
    raceRepository = new RaceRepository();
    CommandAPI.onLoad(
        new CommandAPIBukkitConfig(this)
            .setNamespace("races")
            .skipReloadDatapacks(true)
            .silentLogs(true));
  }

  @Override
  public void onEnable() {
    CommandAPI.onEnable();
  }

  @Override
  public void onDisable() {
    CommandAPI.onDisable();
  }

  private <T extends Listener> T registerListener(T listener) {
    Bukkit.getPluginManager().registerEvents(listener, this);
    return listener;
  }
}
