package me.lemonypancakes.races.power;

import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Map;

public final class PowerRepository {
    private final Map<NamespacedKey, Power> powers;

    public PowerRepository() {
        powers = new HashMap<>();
    }

    public PowerRepository reload() {
        return this;
    }

    public Map<NamespacedKey, Power> getPowers() {
        return powers;
    }

    public Power getPower(NamespacedKey key) {
        return powers.get(key);
    }
}
