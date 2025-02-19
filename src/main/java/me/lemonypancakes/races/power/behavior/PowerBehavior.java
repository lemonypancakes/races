package me.lemonypancakes.races.power.behavior;

import org.bukkit.entity.Player;

public abstract class PowerBehavior<T extends PowerBehavior<T>> {

  public abstract PowerBehaviorInstance<T> apply(Player player);
}
