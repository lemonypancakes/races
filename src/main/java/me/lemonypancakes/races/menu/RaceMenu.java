package me.lemonypancakes.races.menu;

import java.util.ArrayList;
import java.util.List;
import me.lemonypancakes.races.Races;
import me.lemonypancakes.races.RacesPlayer;
import me.lemonypancakes.races.race.Race;
import me.lemonypancakes.races.race.RaceGroup;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public final class RaceMenu extends PaginatedMenu {
  private final List<Race> racesPages;

  public RaceMenu() {
    racesPages = new ArrayList<>();
  }

  public void addLayer(RaceGroup layer) {
    List<Race> races = layer.races();

    races.forEach(
        race -> {
          Inventory menu = Bukkit.createInventory(this, 54);

          menu.setItem(26, race.icon());
        });
  }

  @Override
  public void onClick(InventoryClickEvent event) {
    event.setCancelled(true);
    HumanEntity entity = event.getWhoClicked();

    if (!(entity instanceof Player player)) return;
    RacesPlayer racesPlayer = Races.getPlayer(player);

    if (racesPlayer == null) {}
  }
}
