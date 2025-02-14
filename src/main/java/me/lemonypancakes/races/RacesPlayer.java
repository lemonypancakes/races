package me.lemonypancakes.races;

import java.util.*;
import me.lemonypancakes.races.power.PowerInstance;
import me.lemonypancakes.races.race.Race;
import me.lemonypancakes.races.race.RaceGroup;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public final class RacesPlayer {
  private final Player handle;
  private final Map<RaceGroup, Race> races;
  private final Map<PowerInstance, List<NamespacedKey>> powers;
  private final boolean hasRaceBefore;

  RacesPlayer(Player handle, RacesPlayerData data) {
    this.handle = handle;
    races = new HashMap<>();
    powers = new HashMap<>();
    hasRaceBefore = data.hasRaceBefore();
  }

  public Player getHandle() {
    return handle;
  }

  public Map<RaceGroup, Race> getRaces() {
    return Collections.unmodifiableMap(races);
  }

  public void setRace(RaceGroup group, Race race) {
    races.put(group, race);
    race.powers().forEach(power -> grantPower(new PowerInstance(power, handle), power.key()));
  }

  public Map<PowerInstance, List<NamespacedKey>> getPowers() {
    return Collections.unmodifiableMap(powers);
  }

  public void grantPower(PowerInstance power, NamespacedKey source) {
    powers.put(power, Collections.singletonList(source));
  }

  public void revokePower(PowerInstance power, NamespacedKey source) {
    powers.remove(power);
  }

  public boolean hasRaceBefore() {
    return hasRaceBefore;
  }

  public void tick() {
    powers.keySet().forEach(PowerInstance::tick);
  }
}
