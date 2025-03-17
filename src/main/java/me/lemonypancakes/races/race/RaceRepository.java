package me.lemonypancakes.races.race;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.lemonypancakes.races.Races;
import me.lemonypancakes.races.power.Power;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.packs.resources.Resource;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class RaceRepository {
  private final Map<NamespacedKey, Race> races = new ConcurrentHashMap<>();
  private final Map<NamespacedKey, RaceGroup> groups = new ConcurrentHashMap<>();
  private static final Gson GSON = new Gson();

  @NotNull
  public Collection<Race> getRaces() {
    return Collections.unmodifiableCollection(races.values());
  }

  @NotNull
  public Collection<RaceGroup> getGroups() {
    return Collections.unmodifiableCollection(groups.values());
  }

  @NotNull
  public RaceRepository reload() {
    CraftServer craftServer = (CraftServer) Bukkit.getServer();
    DedicatedServer dedicatedServer = craftServer.getServer();
    Map<ResourceLocation, Resource> raceResources =
        dedicatedServer
            .getResourceManager()
            .listResources("races", s -> s.toString().endsWith(".json"));
    Map<ResourceLocation, Resource> groupResources =
        dedicatedServer
            .getResourceManager()
            .listResources("race_groups", s -> s.toString().endsWith(".json"));

    raceResources.forEach(this::loadRace);
    groupResources.forEach(this::loadRaceGroup);

    return this;
  }

  private void loadRace(ResourceLocation location, Resource resource) {
    NamespacedKey key = parseKey(location);
    if (key == null) return;

    try (InputStreamReader reader = new InputStreamReader(resource.open())) {
      JsonObject object = GSON.fromJson(reader, JsonObject.class);
      if (object == null) return;

      Race race = parseRace(key, object);
      races.put(key, race);
    } catch (Exception e) {
      Bukkit.getLogger().severe("Failed to load race: " + key + " - " + e.getMessage());
    }
  }

  private void loadRaceGroup(ResourceLocation location, Resource resource) {
    NamespacedKey key = parseKey(location);
    if (key == null) return;

    try (InputStreamReader reader = new InputStreamReader(resource.open())) {
      JsonObject object = GSON.fromJson(reader, JsonObject.class);
      if (object == null) return;

      RaceGroup group = parseRaceGroup(key, object);
      groups.put(key, group);
    } catch (Exception e) {
      Bukkit.getLogger().severe("Failed to load race group: " + key + " - " + e.getMessage());
    }
  }

  private NamespacedKey parseKey(ResourceLocation location) {
    return NamespacedKey.fromString(location.toString().replace(".json", ""));
  }

  private Race parseRace(NamespacedKey key, JsonObject object) {
    String name = object.has("name") ? object.get("name").getAsString() : "";
    String description = object.has("description") ? object.get("description").getAsString() : "";
    ItemStack icon =
        object.has("icon")
            ? Bukkit.getItemFactory().createItemStack(object.get("icon").getAsString())
            : new ItemStack(Material.AIR);
    RaceImpact impact =
        object.has("impact")
            ? RaceImpact.valueOf(object.get("impact").getAsString().toUpperCase())
            : RaceImpact.NONE;
    int order = object.has("order") ? object.get("order").getAsInt() : 0;
    List<Power> powers = parsePowers(object.getAsJsonArray("powers"));

    return new Race(key, name, description, icon, impact, powers, order);
  }

  private RaceGroup parseRaceGroup(NamespacedKey key, JsonObject object) {
    String name = object.has("name") ? object.get("name").getAsString() : "";
    List<Race> groupRaces = parseGroupRaces(object.getAsJsonArray("races"));

    return new RaceGroup(key, name, groupRaces);
  }

  private List<Power> parsePowers(JsonArray powersArray) {
    if (powersArray == null) return Collections.emptyList();

    return powersArray.asList().stream()
        .map(element -> NamespacedKey.fromString(element.getAsString()))
        .filter(Objects::nonNull)
        .map(key -> Optional.ofNullable(Races.getPlugin().getPowerRepository().getPower(key)))
        .flatMap(Optional::stream)
        .collect(Collectors.toList());
  }

  private List<Race> parseGroupRaces(JsonArray racesArray) {
    if (racesArray == null) return Collections.emptyList();

    return racesArray.asList().stream()
        .map(element -> NamespacedKey.fromString(element.getAsString()))
        .filter(Objects::nonNull)
        .map(races::get)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }
}
