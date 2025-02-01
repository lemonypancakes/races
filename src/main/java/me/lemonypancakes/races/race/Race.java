package me.lemonypancakes.races.race;

import org.bukkit.inventory.ItemStack;

public record Race(String name, String description, ItemStack icon, RaceImpact impact, int order) {}
