package me.lemonypancakes.races.user;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record UserData(
    @NotNull UUID uuid,
    @NotNull Map<NamespacedKey, List<NamespacedKey>> powers,
    @NotNull Map<NamespacedKey, NamespacedKey> races,
    boolean hasRaceBefore) {}
