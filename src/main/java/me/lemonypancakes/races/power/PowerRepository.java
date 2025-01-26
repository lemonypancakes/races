package me.lemonypancakes.races.power;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.resourcemanagerhelper.Resource;
import me.lemonypancakes.resourcemanagerhelper.ResourceLocation;
import me.lemonypancakes.resourcemanagerhelper.ResourceManagerHelper;
import org.bukkit.NamespacedKey;

import javax.naming.Name;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public final class PowerRepository {
    private final Map<NamespacedKey, Power> powers;

    public PowerRepository() {
        powers = new HashMap<>();
    }

    public PowerRepository reload() {
        Map<ResourceLocation, Resource> resources = ResourceManagerHelper.listResources("powers", s -> s.toString().endsWith(".json"));
        Gson gson = new Gson();

        resources.forEach((resourceLocation, resource) -> {
            try (InputStream inputStream = resource.open()) {
                Reader reader = new InputStreamReader(inputStream);
                JsonObject json = gson.fromJson(reader, JsonObject.class);
                NamespacedKey key = NamespacedKey.fromString(json.get("type").getAsString());
                PowerType<?> type = PowerType.get(key);

                if (type == null) return;
                PowerFactory<?> factory = type.factory();
                Power power = factory.create(key, json);

                powers.put(key, power);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return this;
    }

    public Map<NamespacedKey, Power> getPowers() {
        return powers;
    }

    public Power getPower(NamespacedKey key) {
        return powers.get(key);
    }
}
