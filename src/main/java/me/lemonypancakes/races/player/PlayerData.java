package me.lemonypancakes.races.player;

import org.bukkit.NamespacedKey;

import java.util.List;
import java.util.Map;

public record PlayerData(
    Map<NamespacedKey, NamespacedKey> races,
    Map<NamespacedKey, List<NamespacedKey>> powers,
    boolean hasRaceBefore) {}
