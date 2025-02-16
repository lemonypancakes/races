package me.lemonypancakes.races.plugin;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import me.lemonypancakes.races.Races;
import me.lemonypancakes.races.menu.RaceMenu;
import me.lemonypancakes.races.player.PlayerManager;
import me.lemonypancakes.races.power.PowerRepository;
import me.lemonypancakes.races.race.RaceRepository;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public abstract sealed class Plugin extends JavaPlugin permits FoliaPlugin, PaperPlugin {
  private PlayerManager playerManager;
  private PowerRepository powerRepository;
  private RaceRepository raceRepository;

  public PlayerManager getPlayerManager() {
    return playerManager;
  }

  public PowerRepository getPowerRepository() {
    return powerRepository;
  }

  public RaceRepository getRaceRepository() {
    return raceRepository;
  }

  @Override
  public final void onLoad() {
    Races.setPlugin(this);
    playerManager = new PlayerManager(this);
    powerRepository = new PowerRepository();
    raceRepository = new RaceRepository();
    CommandAPI.onLoad(
        new CommandAPIBukkitConfig(this)
            .setNamespace("races")
            .skipReloadDatapacks(true)
            .silentLogs(true));
  }

  @Override
  public final void onEnable() {
    CommandAPI.onEnable();
    new Metrics(this, 24704);
    registerListener(playerManager);
    setupScheduler();
    Bukkit.getPluginManager()
        .registerEvents(
            new Listener() {
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
            },
            this);
  }

  @Override
  public final void onDisable() {
    CommandAPI.onDisable();
  }

  private <T extends Listener> void registerListener(T listener) {
    Bukkit.getPluginManager().registerEvents(listener, this);
  }

  protected void setupScheduler() {
    Bukkit.getScheduler().runTaskTimer(this, this::tick, 0, 1);
  }

  protected final void tick() {
    playerManager.tick();
  }
}
