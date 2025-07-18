package me.lemonypancakes.races.power;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PowerRepository {
  private final Map<NamespacedKey, Power> powers = new ConcurrentHashMap<>();
  private static final Gson GSON = new Gson();

  @NotNull
  public Collection<Power> getPowers() {
    return Collections.unmodifiableCollection(powers.values());
  }

  @Nullable
  public Power getPower(@NotNull NamespacedKey key) {
    Objects.requireNonNull(key, "key cannot be null");
    return powers.get(key);
  }

  @NotNull
  public PowerRepository reload() {
    CraftServer craftServer = (CraftServer) Bukkit.getServer();
    DedicatedServer dedicatedServer = craftServer.getServer();
    Map<ResourceLocation, Resource> resources =
        dedicatedServer.getResourceManager().listResources("powers", path -> true);

    resources.forEach(this::loadPower);
    return this;
  }

  private void loadPower(ResourceLocation location, Resource resource) {
    NamespacedKey key = parseKey(location);
    if (key == null) return;

    try (InputStreamReader reader = new InputStreamReader(resource.open())) {
      JsonObject object = GSON.fromJson(reader, JsonObject.class);
      if (object == null) return;

      Power power = parsePower(key, object);
      powers.put(key, power);
    } catch (Exception e) {
      Bukkit.getLogger().severe("Failed to load power: " + key + " - " + e.getMessage());
    }
  }

  private NamespacedKey parseKey(ResourceLocation location) {
    return NamespacedKey.fromString(location.toString().replace(".json", ""));
  }

  private Power parsePower(NamespacedKey key, JsonObject object) {
    String name = object.has("name") ? object.get("name").getAsString() : "";
    String displayName = object.has("display_name") ? object.get("display_name").getAsString() : "";
    String description = object.has("description") ? object.get("description").getAsString() : "";
    ItemStack icon =
        object.has("icon")
            ? Bukkit.getItemFactory().createItemStack(object.get("icon").getAsString())
            : new ItemStack(Material.AIR);
    List<PowerBehavior<?>> behaviors = parseBehaviors(object.getAsJsonArray("behaviors"));

    return new Power(key, name, displayName, description, icon, behaviors);
  }

  private List<PowerBehavior<?>> parseBehaviors(JsonArray behaviorsArray) {
    if (behaviorsArray == null) return Collections.emptyList();

    List<PowerBehavior<?>> behaviors = new ArrayList<>();
    behaviorsArray.forEach(
        element -> {
          if (!element.isJsonObject()) return;
          JsonObject behaviorObject = element.getAsJsonObject();
          NamespacedKey typeKey =
              NamespacedKey.fromString(behaviorObject.get("type").getAsString());

          Optional.ofNullable(typeKey)
              .map(PowerBehaviorTypes::get)
              .map(PowerBehaviorType::factory)
              .map(factory -> factory.create(behaviorObject))
              .ifPresent(behaviors::add);
        });

    return behaviors;
  }
}
