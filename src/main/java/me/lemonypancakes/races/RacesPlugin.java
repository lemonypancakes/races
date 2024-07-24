package me.lemonypancakes.races;

import me.lemonypancakes.races.power.PowerInstance;
import me.lemonypancakes.races.power.PowerRepository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
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
    }

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onPlayerMove(PlayerMoveEvent event) {
            }
        }, this);
    }

    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PowerInstance.removeAll(player);
        }
    }
}
