package me.lemonypancakes.races.power;

import me.lemonypancakes.resourcemanagerhelper.Resource;
import me.lemonypancakes.resourcemanagerhelper.ResourceLocation;
import me.lemonypancakes.resourcemanagerhelper.ResourceManagerHelper;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Map;

public final class PowerRepository {
    private final Map<NamespacedKey, Power> powers;

    public PowerRepository() {
        powers = new HashMap<>();
    }

    public void reload() {
        Map<ResourceLocation, Resource> resources = ResourceManagerHelper.listResources("power", s -> s.toString().endsWith(".json"));
    }

    public Map<NamespacedKey, Power> getPowers() {
        return powers;
    }

    public Power getPower(NamespacedKey key) {
        return powers.get(key);
    }
}
