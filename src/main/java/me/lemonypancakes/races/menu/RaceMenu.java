package me.lemonypancakes.races.menu;

import me.lemonypancakes.races.race.Race;
import me.lemonypancakes.races.race.RaceGroup;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

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

          menu.setItem(26, new ItemStack(Material.ACACIA_PLANKS));
        });
    }
}
