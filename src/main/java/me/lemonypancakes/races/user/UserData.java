package me.lemonypancakes.races.user;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public record UserData(
    @NotNull UUID uuid,
    @NotNull Map<NamespacedKey, NamespacedKey> races,
    @NotNull Map<NamespacedKey, List<NamespacedKey>> powers,
    boolean hasRaceBefore) {}
