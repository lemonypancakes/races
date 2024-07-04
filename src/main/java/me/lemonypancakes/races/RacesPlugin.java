package me.lemonypancakes.races;

import org.bukkit.plugin.java.JavaPlugin;

public final class RacesPlugin extends JavaPlugin {
    @Override
    public void onLoad() {
        Races.setPlugin(this);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
