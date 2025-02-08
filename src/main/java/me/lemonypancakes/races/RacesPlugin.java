package me.lemonypancakes.races;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import me.lemonypancakes.races.menu.RaceMenu;
import me.lemonypancakes.races.power.PowerRepository;
import me.lemonypancakes.races.race.RaceRepository;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

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
    playerManager = new RacesPlayerManager(this);
    powerRepository = new PowerRepository(this);
    raceRepository = new RaceRepository(this);
    CommandAPI.onLoad(
        new CommandAPIBukkitConfig(this)
            .setNamespace("races")
            .skipReloadDatapacks(true)
            .silentLogs(true));
  }

  @Override
  public void onEnable() {
    CommandAPI.onEnable();
    registerListener(playerManager);
    setupScheduler();
    Bukkit.getPluginManager().registerEvents(new Listener() {
      @EventHandler
      private void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        if (!(inventory.getHolder() instanceof RaceMenu menu)) return;

        menu.onClick(event);
      }

      @EventHandler
      private void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();

        if (!(inventory.getHolder() instanceof RaceMenu menu)) return;

        menu.onClose(event);
      }
    }, this);
  }

  @Override
  public void onDisable() {
    CommandAPI.onDisable();
  }

  private <T extends Listener> void registerListener(T listener) {
    Bukkit.getPluginManager().registerEvents(listener, this);
  }

  private void setupScheduler() {
    new BukkitRunnable() {
      @Override
      public void run() {
        playerManager.tick();
      }
    }.runTaskTimer(this, 0L, 1L);
  }

  private boolean isPaper() {
    return Bukkit.getServer().getName().contains("Paper");
  }
}
