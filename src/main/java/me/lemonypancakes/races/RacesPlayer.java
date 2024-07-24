package me.lemonypancakes.races;

import me.lemonypancakes.races.power.Power;
import me.lemonypancakes.races.power.PowerInstance;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class RacesPlayer {
    private final Player player;
    private final List<PowerInstance<?>> powers;

    RacesPlayer(Player player) {
        this.player = player;
        this.powers = new ArrayList<>();
    }

    public void addPower(final Power power) {
        power.apply(player).add();
    }

    public void removePower(final Power power) {

    }
}
