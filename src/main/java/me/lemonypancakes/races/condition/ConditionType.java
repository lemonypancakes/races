package me.lemonypancakes.races.condition;

import org.bukkit.NamespacedKey;

public record ConditionType<T>(
    Class<T> typeClass, NamespacedKey key, ConditionFactory<T> factory) {}
