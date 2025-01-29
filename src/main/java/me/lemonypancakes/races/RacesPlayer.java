package me.lemonypancakes.races;

import java.util.ArrayList;
import java.util.List;
import me.lemonypancakes.races.power.PowerInstance;
import org.bukkit.entity.Player;

public final class RacesPlayer {
  private final Player handle;
  private final List<PowerInstance> powers;

  RacesPlayer(Player handle) {
    this.handle = handle;
    this.powers = new ArrayList<>();
  }

  public Player getHandle() {
    return handle;
  }

  public void addPower(PowerInstance power) {
    powers.add(power);
  }

  public void removePower(PowerInstance power) {
    powers.remove(power);
  }

  public void tick() {
    powers.forEach(PowerInstance::tick);
  }
}
