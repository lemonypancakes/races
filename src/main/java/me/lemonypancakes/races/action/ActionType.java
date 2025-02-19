package me.lemonypancakes.races.action;

import org.bukkit.NamespacedKey;

public record ActionType<T>(Class<T> typeClass, NamespacedKey key, ActionFactory<T> factory) {}
