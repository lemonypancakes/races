package me.lemonypancakes.races;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import me.lemonypancakes.races.menu.RaceMenu;
import me.lemonypancakes.races.power.PowerRepository;
import me.lemonypancakes.races.race.RaceRepository;
import me.lemonypancakes.races.user.UserList;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public final class RacesPlugin extends JavaPlugin {
  private UserList userList;
  private PowerRepository powerRepository;
  private RaceRepository raceRepository;

  public UserList getUserManager() {
    return userList;
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
    userList = new UserList(this);
    powerRepository = new PowerRepository().reload();
    raceRepository = new RaceRepository().reload();
    CommandAPI.onLoad(
        new CommandAPIBukkitConfig(this)
            .setNamespace("races")
            .skipReloadDatapacks(true)
            .silentLogs(true));
  }

  @Override
  public void onEnable() {
    CommandAPI.onEnable();
    new Metrics(this, 24704);
    registerListener(userList);
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
  public void onDisable() {
    CommandAPI.onDisable();
  }

  private <T extends Listener> void registerListener(T listener) {
    Bukkit.getPluginManager().registerEvents(listener, this);
  }

  private void setupScheduler() {
    Bukkit.getScheduler().runTaskTimer(this, this::tick, 0, 1);
  }

  private void tick() {
    userList.tick();
  }
}
