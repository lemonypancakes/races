package me.lemonypancakes.races;

import me.lemonypancakes.races.power.Power;
import me.lemonypancakes.races.power.PowerInstance;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class RacesPlayer {
    private final List<PowerInstance<?>> activePowers;

    public RacesPlayer(Player player) {
        this.activePowers = new ArrayList<>();
    }

    public void addPower(final Power power) {
    }
}
