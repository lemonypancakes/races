package me.lemonypancakes.races.user;

import java.util.*;
import me.lemonypancakes.races.event.user.UserRaceSetEvent;
import me.lemonypancakes.races.power.Power;
import me.lemonypancakes.races.power.PowerInstance;
import me.lemonypancakes.races.race.Race;
import me.lemonypancakes.races.race.RaceGroup;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class User {
  private final Player player;
  private final Map<RaceGroup, Race> races;
  private final Map<PowerInstance, List<NamespacedKey>> powers;
  private final boolean hasRaceBefore;

  User(@NotNull Player player, @NotNull UserData data) {
    this.player = Objects.requireNonNull(player, "player cannot be null");
    this.races = new HashMap<>();
    this.powers = new HashMap<>();
    this.hasRaceBefore = data.hasRaceBefore();
  }

  @NotNull
  public Player getPlayer() {
    return player;
  }

  @NotNull
  public Map<RaceGroup, Race> getRaces() {
    return Collections.unmodifiableMap(races);
  }

  public void setRace(@NotNull RaceGroup group, @Nullable Race newRace) {
    Objects.requireNonNull(group, "group cannot be null");
    Race oldRace = races.get(group);
    UserRaceSetEvent event = new UserRaceSetEvent(this, group, oldRace, newRace);
    Bukkit.getPluginManager().callEvent(event);
    newRace = event.getNewRace();

    if (Objects.equals(oldRace, newRace)) return;

    if (oldRace != null) {
      for (Power power : oldRace.powers()) {
        powers
            .entrySet()
            .removeIf(
                entry -> {
                  PowerInstance instance = entry.getKey();
                  List<NamespacedKey> sources = entry.getValue();
                  if (instance.getPower().equals(power) && sources.contains(oldRace.key())) {
                    sources.remove(oldRace.key());
                    return sources.isEmpty();
                  }
                  return false;
                });
      }
    }

    if (newRace != null) {
      for (Power power : newRace.powers()) {
        PowerInstance instance = new PowerInstance(power, player);
        grantPower(instance, newRace.key());
      }
    }

    races.put(group, newRace);
  }

  public Map<PowerInstance, List<NamespacedKey>> getPowers() {
    return Collections.unmodifiableMap(powers);
  }

  public void grantPower(PowerInstance power, NamespacedKey source) {
    powers.computeIfAbsent(power, k -> new ArrayList<>()).add(source);
  }

  public void revokePower(PowerInstance power, NamespacedKey source) {
    List<NamespacedKey> sources = powers.get(power);
    if (sources != null) {
      sources.remove(source);
      if (sources.isEmpty()) {
        powers.remove(power);
      }
    }
  }

  public boolean hasRaceBefore() {
    return hasRaceBefore;
  }

  public void tick() {
    powers.keySet().forEach(PowerInstance::tick);
  }
}
