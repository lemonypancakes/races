package me.lemonypancakes.races.power;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import me.lemonypancakes.races.Races;
import me.lemonypancakes.races.power.behavior.PowerBehavior;
import me.lemonypancakes.races.power.behavior.PowerBehaviorType;
import me.lemonypancakes.resourcemanagerhelper.Resource;
import me.lemonypancakes.resourcemanagerhelper.ResourceLocation;
import me.lemonypancakes.resourcemanagerhelper.ResourceManagerHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public final class PowerRepository {
  private final Map<NamespacedKey, Power> powers;

  public PowerRepository() {
    powers = new HashMap<>();
  }

  public Collection<Power> getPowers() {
    return Collections.unmodifiableCollection(powers.values());
  }

  public Power getPower(NamespacedKey key) {
    return powers.get(key);
  }

  public PowerRepository reload() {
    Map<ResourceLocation, Resource> resources =
        ResourceManagerHelper.listResources("powers", s -> s.toString().endsWith(".json"));
    Gson gson = new Gson();

    resources.forEach(
        (location, resource) -> {
          try {
            InputStream stream = resource.open();
            Reader reader = new InputStreamReader(stream);
            JsonObject json = gson.fromJson(reader, JsonObject.class);

            if (json == null) return;
            String name = "";
            String displayName = "";
            String description = "";
            ItemStack icon = new ItemStack(Material.STONE);
            PowerBehavior<?> behavior = null;

            if (json.has("name")) {
              name = json.get("name").getAsString();
            }
            if (json.has("display_name")) {
              displayName = json.get("display_name").getAsString();
            }
            if (json.has("description")) {
              description = json.get("description").getAsString();
            }
            if (json.has("icon")) {
              icon = Bukkit.getItemFactory().createItemStack(json.get("icon").getAsString());
            }
            if (json.has("behavior")) {
              if (!json.get("behavior").isJsonObject()) return;
              JsonObject behaviorJson = json.getAsJsonObject("behavior");

              if (!behaviorJson.has("type")) return;
              String s = behaviorJson.get("type").getAsString();

              if (s == null) return;
              NamespacedKey key = NamespacedKey.fromString(s);

              if (key == null) return;
              PowerBehaviorType<?> type = PowerBehaviorType.get(key);

              if (type == null) return;
              behavior = type.factory().create(behaviorJson);
              new Power(Races.namespace(""), name, displayName, description, icon, null);
            }
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
    return this;
  }
}
