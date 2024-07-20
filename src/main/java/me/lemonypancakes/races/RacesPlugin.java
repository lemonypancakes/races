package me.lemonypancakes.races;

import me.lemonypancakes.races.power.PowerRepository;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class RacesPlugin extends JavaPlugin {
    private List<RacesPlayer> players;
    private PowerRepository powerRepository;

    public List<RacesPlayer> getPlayers() {
        return players;
    }

    public PowerRepository getPowerRepository() {
        return powerRepository;
    }

    public void onLoad() {
        Races.setPlugin(this);
        players = new ArrayList<>();
        powerRepository = new PowerRepository();
    }

    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }
}
