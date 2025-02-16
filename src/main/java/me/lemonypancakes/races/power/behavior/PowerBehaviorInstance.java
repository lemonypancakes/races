package me.lemonypancakes.races.power.behavior;

import org.bukkit.entity.Player;

public abstract class PowerBehaviorInstance<T extends PowerBehavior<T>> {
  protected final T behavior;
  protected final Player player;
  private boolean active;

  public PowerBehaviorInstance(T behavior, Player player) {
    this.behavior = behavior;
    this.player = player;
  }

  public final T getBehavior() {
    return behavior;
  }

  public final Player getPlayer() {
    return player;
  }

  public final boolean isActive() {
    return active;
  }

  protected abstract void onAdd();

  public final void add() {
    if (!active) {
      active = true;
      onAdd();
    }
  }

  protected void onGrant() {
    onAdd();
  }

  public final void grant() {
    if (!active) {
      active = true;
      onGrant();
    }
  }

  protected abstract void onRemove();

  public final void remove() {
    if (active) {
      active = false;
      onRemove();
    }
  }

  protected void onRevoke() {
    onRemove();
  }

  public final void revoke() {
    if (active) {
      active = false;
      onRevoke();
    }
  }

  protected void onTick() {}

  public final void tick() {
    if (active) {
      onTick();
    }
  }
}
