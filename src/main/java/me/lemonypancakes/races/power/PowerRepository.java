package me.lemonypancakes.races.power;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import me.lemonypancakes.races.power.behavior.PowerBehavior;
import me.lemonypancakes.races.power.behavior.PowerBehaviorType;
import me.lemonypancakes.races.power.behavior.PowerBehaviorTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.packs.resources.Resource;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.CraftServer;
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
    CraftServer craftServer = (CraftServer) Bukkit.getServer();
    DedicatedServer dedicatedServer = craftServer.getServer();
    Map<ResourceLocation, Resource> resources =
        dedicatedServer
            .getResourceManager()
            .listResources("powers", s -> s.toString().endsWith(".json"));
    Gson gson = new Gson();

    resources.forEach(
        (location, resource) -> {
          NamespacedKey key = NamespacedKey.fromString(location.toString());

          if (key == null) return;
          try {
            InputStream stream = resource.open();
            Reader reader = new InputStreamReader(stream);
            JsonObject object = gson.fromJson(reader, JsonObject.class);

            if (object == null) return;
            String name = "";
            String displayName = "";
            String description = "";
            ItemStack icon = new ItemStack(Material.AIR);
            List<PowerBehavior<?>> behaviors = new ArrayList<>();

            if (object.has("name")) name = object.get("name").getAsString();
            if (object.has("display_name")) displayName = object.get("display_name").getAsString();
            if (object.has("description")) description = object.get("description").getAsString();
            if (object.has("icon"))
              icon = Bukkit.getItemFactory().createItemStack(object.get("icon").getAsString());

            if (object.has("behaviors")) {
              if (!object.get("behaviors").isJsonArray()) return;
              JsonArray behaviorsArray = object.getAsJsonArray("behaviors");

              behaviorsArray.forEach(
                  element -> {
                    if (!element.isJsonObject()) return;
                    JsonObject behaviorObject = element.getAsJsonObject();
                    String typeString = behaviorObject.get("type").getAsString();
                    NamespacedKey typeKey = NamespacedKey.fromString(typeString);

                    if (typeKey == null) return;
                    PowerBehaviorType<?> type = PowerBehaviorTypes.get(typeKey);

                    if (type == null) return;
                    PowerBehavior<?> behavior = type.factory().create(behaviorObject);
                    behaviors.add(behavior);
                  });
            }
            powers.put(key, new Power(key, name, displayName, description, icon, behaviors));
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
    return this;
  }
}
