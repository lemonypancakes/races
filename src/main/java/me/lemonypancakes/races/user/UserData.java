package me.lemonypancakes.races.user;

import java.util.List;
import java.util.Map;
import org.bukkit.NamespacedKey;

public record UserData(
    Map<NamespacedKey, NamespacedKey> races,
    Map<NamespacedKey, List<NamespacedKey>> powers,
    boolean hasRaceBefore) {}
