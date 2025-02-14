package me.lemonypancakes.races.power;

import java.util.Objects;
import me.lemonypancakes.races.power.behavior.PowerBehaviorInstance;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class PowerInstance {
  private final Power power;
  private final Player player;
  private final PowerBehaviorInstance<?> behavior;
  private State state;

  public PowerInstance(@NotNull Power power, @NotNull Player player) {
    this.power = Objects.requireNonNull(power, "power cannot be null");
    this.player = Objects.requireNonNull(player, "player cannot be null");
    behavior = power.behavior().apply(player);
  }

  public Power getPower() {
    return power;
  }

  public Player getPlayer() {
    return player;
  }

  public State getState() {
    return state;
  }

  public boolean isActive() {
    return state == State.GRANTED || state == State.ADDED;
  }

  public boolean grant() {
    if (isActive()) return false;
    state = State.GRANTED;
    behavior.grant();
    return true;
  }

  public boolean revoke() {
    if (!isActive()) return false;
    state = State.REVOKED;
    behavior.revoke();
    return true;
  }

  public boolean add() {
    if (isActive()) return false;
    state = State.ADDED;
    behavior.add();
    return true;
  }

  public boolean remove() {
    if (!isActive()) return false;
    state = State.REMOVED;
    behavior.remove();
    return true;
  }

  public boolean tick() {
    if (!isActive()) return false;
    behavior.tick();
    return true;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (!(object instanceof PowerInstance that)) return false;

    return power.equals(that.power) && player.equals(that.player);
  }

  @Override
  public int hashCode() {
    int result = power.hashCode();
    result = 31 * result + player.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return power.toString();
  }

  public enum State {
    GRANTED,
    REVOKED,
    ADDED,
    REMOVED
  }
}
