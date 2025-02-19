package me.lemonypancakes.races.race;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
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
          NamespacedKey key = NamespacedKey.fromString(location.toString());

          if (key == null) return;
          try {
            InputStream stream = resource.open();
            Reader reader = new InputStreamReader(stream);
            JsonObject object = gson.fromJson(reader, JsonObject.class);

            if (object == null) return;
            String name = "";
            String description = "";
            ItemStack icon = new ItemStack(Material.AIR);
            RaceImpact impact = RaceImpact.NONE;
            List<Power> powers = new ArrayList<>();
            int order = 0;

            if (object.has("name")) name = object.get("name").getAsString();
            if (object.has("description")) description = object.get("description").getAsString();

            if (object.has("icon")) {
              String iconString = object.get("icon").getAsString();
              icon = Bukkit.getItemFactory().createItemStack(iconString);
            }

            if (object.has("impact")) {
              impact = RaceImpact.valueOf(object.get("impact").getAsString().toUpperCase());
            }

            if (object.has("powers")) {
              String[] powersStrings = gson.fromJson(object.get("powers"), String[].class);
              Arrays.stream(powersStrings)
                  .forEach(
                      string -> {
                        Power power =
                            Races.getPlugin()
                                .getPowerRepository()
                                .getPower(NamespacedKey.fromString(string));
                        powers.add(power);
                      });
            }
            races.put(key, new Race(key, name, description, icon, impact, powers, order));
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
    return this;
  }
}
