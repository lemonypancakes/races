package me.lemonypancakes.races;

import java.util.ArrayList;
import java.util.List;
import me.lemonypancakes.races.power.PowerInstance;
import org.bukkit.entity.Player;

public final class RacesPlayer {
  private final Player handle;
  private final List<PowerInstance> powers;

  RacesPlayer(final Player handle) {
    this.handle = handle;
    this.powers = new ArrayList<>();
  }

  public Player getHandle() {
    return this.handle;
  }

  public void addPower(final PowerInstance power) {
    this.powers.add(power);
  }

  public void removePower(final PowerInstance power) {
    this.powers.remove(power);
  }

  public void tick() {
    this.powers.forEach(PowerInstance::tick);
  }
}
