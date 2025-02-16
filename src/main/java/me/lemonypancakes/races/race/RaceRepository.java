package me.lemonypancakes.races.race;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import me.lemonypancakes.races.Races;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.packs.resources.Resource;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.CraftServer;

public final class RaceRepository {
  private final Map<NamespacedKey, Race> races;
  private final Map<NamespacedKey, RaceGroup> groups;

  public RaceRepository() {
    races = new HashMap<>();
    groups = new HashMap<>();
  }

  public Collection<Race> getRaces() {
    return Collections.unmodifiableCollection(races.values());
  }

  public Collection<RaceGroup> getGroups() {
    return Collections.unmodifiableCollection(groups.values());
  }

  public RaceRepository reload() {
    CraftServer craftServer = (CraftServer) Bukkit.getServer();
    DedicatedServer dedicatedServer = craftServer.getServer();
    Map<ResourceLocation, Resource> resources =
        dedicatedServer
            .getResourceManager()
            .listResources("races", s -> s.toString().endsWith(".json"));
    Gson gson = new Gson();

    resources.forEach(
        (location, resource) -> {
          try {
            InputStream stream = resource.open();
            Reader reader = new InputStreamReader(stream);
            JsonObject object = gson.fromJson(reader, JsonObject.class);

            if (object == null) return;
            Race race = new Race(Races.namespace("test"), null, null, null, null, null, 0);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
    return this;
  }
}
