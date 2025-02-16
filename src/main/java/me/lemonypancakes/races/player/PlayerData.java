package me.lemonypancakes.races.player;

import java.util.List;
import java.util.Map;
import org.bukkit.NamespacedKey;

public record PlayerData(
    Map<NamespacedKey, NamespacedKey> races,
    Map<NamespacedKey, List<NamespacedKey>> powers,
    boolean hasRaceBefore) {}
