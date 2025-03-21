package me.lemonypancakes.races.power;

import me.lemonypancakes.races.power.behavior.PowerBehaviorInstance;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class PowerInstance {
  private final Power power;
  private final Player player;
  private final List<PowerBehaviorInstance<?>> behaviors;
  private PowerState state;

  public PowerInstance(@NotNull Power power, @NotNull Player player) {
    this.power = Objects.requireNonNull(power, "power cannot be null");
    this.player = Objects.requireNonNull(player, "player cannot be null");
    behaviors =
        power.behaviors().stream()
            .map(behavior -> behavior.apply(player))
            .collect(Collectors.toList());
    state = PowerState.REVOKED;
  }

  @NotNull
  public Power getPower() {
    return power;
  }

  @NotNull
  public Player getPlayer() {
    return player;
  }

  @NotNull
  public PowerState getState() {
    return state;
  }

  public boolean isActive() {
    return state == PowerState.GRANTED || state == PowerState.ADDED;
  }

  public boolean grant() {
    if (isActive()) return false;
    state = PowerState.GRANTED;
    behaviors.forEach(PowerBehaviorInstance::grant);
    return true;
  }

  public boolean revoke() {
    if (!isActive()) return false;
    state = PowerState.REVOKED;
    behaviors.forEach(PowerBehaviorInstance::revoke);
    return true;
  }

  public boolean add() {
    if (isActive()) return false;
    state = PowerState.ADDED;
    behaviors.forEach(PowerBehaviorInstance::add);
    return true;
  }

  public boolean remove() {
    if (!isActive()) return false;
    state = PowerState.REMOVED;
    behaviors.forEach(PowerBehaviorInstance::remove);
    return true;
  }

  public boolean tick() {
    if (!isActive()) return false;
    behaviors.forEach(PowerBehaviorInstance::tick);
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof PowerInstance that)) return false;

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
}
