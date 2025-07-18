package me.lemonypancakes.races.menu;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import me.lemonypancakes.races.Races;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class PaginatedMenu extends Menu {
  protected final List<Inventory> pages = new LinkedList<>();
  protected final Set<Player> exemptions = new HashSet<>();

  public boolean open(Player player) {
    if (!pages.isEmpty()) {
      Inventory inventory = pages.getFirst();
      if (inventory != null) {
        player.openInventory(inventory);
        return true;
      }
    }

    return false;
  }

  public void addExemption(final Player player) {
    exemptions.add(player);
    new BukkitRunnable() {
      public void run() {
        exemptions.remove(player);
      }
    }.runTaskLater(Races.getPlugin(), 20L);
  }

  public void removeExemption(Player player) {
    exemptions.remove(player);
  }

  public boolean isExempted(Player player) {
    return exemptions.contains(player);
  }
}
