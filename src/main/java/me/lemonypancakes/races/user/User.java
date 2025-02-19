package me.lemonypancakes.races.user;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.lemonypancakes.races.power.PowerInstance;
import me.lemonypancakes.races.race.Race;
import me.lemonypancakes.races.race.RaceGroup;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public final class User {
  private final Player player;
  private final Map<RaceGroup, Race> races;
  private final Map<PowerInstance, List<NamespacedKey>> powers;
  private final boolean hasRaceBefore;

  User(Player player, UserData data) {
    this.player = player;
    races = new HashMap<>();
    powers = new HashMap<>();
    hasRaceBefore = data.hasRaceBefore();
  }

  public Player getPlayer() {
    return player;
  }

  public Map<RaceGroup, Race> getRaces() {
    return Collections.unmodifiableMap(races);
  }

  public void setRace(RaceGroup group, Race race) {
    races.put(group, race);
    race.powers().forEach(power -> grantPower(new PowerInstance(power, player), power.key()));
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
