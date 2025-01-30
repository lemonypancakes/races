package me.lemonypancakes.races.power;

import java.util.Objects;
import me.lemonypancakes.races.power.behavior.PowerBehaviorInstance;
import org.bukkit.entity.Player;

public class PowerInstance {
  private final Power power;
  private final Player player;
  private final PowerBehaviorInstance<?> behavior;
  private State state;

  public PowerInstance(final Power power, final Player player) {
    this.power = Objects.requireNonNull(power, "power must not be null");
    this.player = Objects.requireNonNull(player, "player must not be null");
    this.behavior = power.behavior().apply(player);
  }

  public final Power getPower() {
    return this.power;
  }

  public final Player getPlayer() {
    return this.player;
  }

  public final State getState() {
    return this.state;
  }

  public final boolean isActive() {
    return this.state == State.GRANTED || this.state == State.ADDED;
  }

  public final boolean grant() {
    if (isActive()) return false;
    this.state = State.GRANTED;
    this.behavior.grant();
    return true;
  }

  public final boolean revoke() {
    if (!isActive()) return false;
    this.state = State.REVOKED;
    this.behavior.revoke();
    return true;
  }

  public final boolean add() {
    if (isActive()) return false;
    this.state = State.ADDED;
    this.behavior.add();
    return true;
  }

  public final boolean remove() {
    if (!isActive()) return false;
    this.state = State.REMOVED;
    this.behavior.remove();
    return true;
  }

  public final boolean tick() {
    if (!isActive()) return false;
    this.behavior.tick();
    return true;
  }

  @Override
  public final boolean equals(final Object object) {
    if (this == object) return true;
    if (!(object instanceof final PowerInstance that)) return false;

    return this.power.equals(that.power) && this.player.equals(that.player);
  }

  @Override
  public final int hashCode() {
    int result = this.power.hashCode();
    result = 31 * result + this.player.hashCode();
    return result;
  }

  @Override
  public final String toString() {
    return this.power.toString();
  }

  public enum State {
    GRANTED,
    REVOKED,
    ADDED,
    REMOVED
  }
}
