package me.lemonypancakes.races.plugin;

import org.bukkit.Bukkit;

public final class FoliaPlugin extends Plugin {
  @Override
  protected void setupScheduler() {
    Bukkit.getGlobalRegionScheduler().runAtFixedRate(this, task -> this.tick(), 0, 1);
  }
}
