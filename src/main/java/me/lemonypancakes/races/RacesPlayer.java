package me.lemonypancakes.races;

import java.util.*;
import me.lemonypancakes.races.power.PowerInstance;
import org.bukkit.entity.Player;

public final class RacesPlayer {
  private final Player handle;
  private final UUID uuid;
  private final List<PowerInstance> powers;

  RacesPlayer(Player handle) {
    this.handle = handle;
    uuid = handle.getUniqueId();
    powers = new ArrayList<>();
  }

  public Player getHandle() {
    return handle;
  }

  public UUID getUuid() {
    return uuid;
  }

  public Collection<PowerInstance> getPowers() {
    return Collections.unmodifiableCollection(powers);
  }

  public void grantPower(PowerInstance power) {
    powers.add(power);
  }

  public void revokePower(PowerInstance power) {
    powers.remove(power);
  }

  public void tick() {
    powers.forEach(PowerInstance::tick);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;

    RacesPlayer that = (RacesPlayer) o;
    return uuid.equals(that.uuid);
  }

  @Override
  public int hashCode() {
    return uuid.hashCode();
  }

  @Override
  public String toString() {
    return "RacesPlayer{" + "handle=" + handle + '}';
  }
}
