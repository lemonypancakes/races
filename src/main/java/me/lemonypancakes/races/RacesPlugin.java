package me.lemonypancakes.races;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import me.lemonypancakes.races.power.PowerInstance;
import me.lemonypancakes.races.power.PowerRepository;
import me.lemonypancakes.races.race.RaceRepository;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R3.CraftServer;
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public final class RacesPlugin extends JavaPlugin {
    private List<RacesPlayer> players;
    private PowerRepository powerRepository;
    private RaceRepository raceRepository;

    public List<RacesPlayer> getPlayers() {
        return players;
    }

    public PowerRepository getPowerRepository() {
        return powerRepository;
    }

    public RaceRepository getRaceRepository() {
        return raceRepository;
    }

    public void onLoad() {
        Races.setPlugin(this);
        players = new ArrayList<>();
        powerRepository = new PowerRepository().reload();
        raceRepository = new RaceRepository();
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this));
    }

    public void onEnable() {
        CommandAPI.onEnable();
        Class<?> pClass = Player.class;
        Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[] { Player.class }, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equals("sendBlockChange")) { // or some other logic
                            System.out.println("before");
                            Object returnValue = method.invoke(pClass, args);
                            System.out.println("after");
                            return returnValue;
                        }
                        return method.invoke(pClass);
                    }
                });
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendBlockChange(player.getLocation(), Material.DIAMOND_BLOCK.createBlockData());
                }
            }
        }.runTaskTimer(this, 0, 1);
    }

    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PowerInstance.removeAll(player);
        }
    }
}
