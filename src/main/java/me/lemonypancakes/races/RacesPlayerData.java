package me.lemonypancakes.races;

import java.util.List;
import java.util.Map;
import org.bukkit.NamespacedKey;

public record RacesPlayerData(
    Map<NamespacedKey, NamespacedKey> races,
    Map<NamespacedKey, List<NamespacedKey>> powers,
    boolean hasRaceBefore) {}
