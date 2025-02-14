package me.lemonypancakes.races.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public abstract class Menu implements InventoryHolder {
  protected Inventory inventory;

  @Override
  public final @NotNull Inventory getInventory() {
    return inventory;
  }

  public boolean open(Player player) {
    if (inventory == null) return false;
    player.openInventory(inventory);
    return true;
  }

  public void onClick(InventoryClickEvent event) {}

  public void onClose(InventoryCloseEvent event) {}
}
