package me.lemonypancakes.races.power.behavior;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class PowerBehavior<T extends PowerBehavior<T>> {
  @NotNull
  public abstract PowerBehaviorInstance<T> apply(@NotNull Player player);
}
