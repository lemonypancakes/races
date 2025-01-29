package me.lemonypancakes.races.power;

import java.util.Objects;
import me.lemonypancakes.races.power.behavior.PowerBehaviorInstance;
import org.bukkit.entity.Player;

public class PowerInstance {
  private final Power power;
  private final Player player;
  private State state;
  private final PowerBehaviorInstance<?> behavior;

  public PowerInstance(final Power power, final Player player) {
    this.power = Objects.requireNonNull(power, "power must not be null");
    this.player = Objects.requireNonNull(player, "player must not be null");
    this.behavior = power.behavior().apply(player);
  }

  public final Power getPower() {
    return power;
  }

  public final Player getPlayer() {
    return player;
  }

  public final State getState() {
    return state;
  }

  public final boolean isActive() {
    return state == State.GRANTED || state == State.ADDED;
  }

  public final boolean grant() {
    if (isActive()) return false;
    state = State.GRANTED;
    behavior.grant();
    return true;
  }

  public final boolean revoke() {
    if (!isActive()) return false;
    state = State.REVOKED;
    behavior.revoke();
    return true;
  }

  public final boolean add() {
    if (isActive()) return false;
    state = State.ADDED;
    behavior.add();
    return true;
  }

  public final boolean remove() {
    if (!isActive()) return false;
    state = State.REMOVED;
    behavior.remove();
    return true;
  }

  public final boolean tick() {
    if (!isActive()) return false;
    behavior.tick();
    return true;
  }

  @Override
  public final boolean equals(Object object) {
    if (this == object) return true;
    if (!(object instanceof PowerInstance that)) return false;

    return power.equals(that.power) && player.equals(that.player);
  }

  @Override
  public final int hashCode() {
    int result = power.hashCode();
    result = 31 * result + player.hashCode();
    return result;
  }

  @Override
  public final String toString() {
    return power.toString();
  }

  public enum State {
    GRANTED,
    REVOKED,
    ADDED,
    REMOVED
  }
}
