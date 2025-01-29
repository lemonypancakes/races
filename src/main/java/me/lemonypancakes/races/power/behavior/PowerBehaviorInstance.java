package me.lemonypancakes.races.power.behavior;

import org.bukkit.entity.Player;

public abstract class PowerBehaviorInstance<T extends PowerBehavior<T>> {
  protected final T behavior;
  protected final Player player;

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

  public void grant() {
    add();
  }

  public void revoke() {
    remove();
  }

  public abstract void add();

  public abstract void remove();

  public void tick() {}
}
