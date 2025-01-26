package me.lemonypancakes.races;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import me.lemonypancakes.races.power.PowerInstance;
import me.lemonypancakes.races.power.PowerRepository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
        powerRepository = new PowerRepository().reload();
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this));
    }

    public void onEnable() {
        CommandAPI.onEnable();
    }

    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PowerInstance.removeAll(player);
        }
    }
}
