package me.lemonypancakes.races.power.behavior;

import org.bukkit.NamespacedKey;

public record PowerBehaviorType<T extends PowerBehavior<T>>(
    NamespacedKey key, PowerBehaviorFactory<T> factory) {}
