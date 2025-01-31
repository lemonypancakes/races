package me.lemonypancakes.races.power.behavior;

import org.bukkit.entity.Player;

public final class AreaOfEffectPowerBehavior extends PowerBehavior<AreaOfEffectPowerBehavior> {
  @Override
  public PowerBehaviorInstance<AreaOfEffectPowerBehavior> apply(Player player) {
    return new Instance(this, player);
  }

  public static final class Instance extends PowerBehaviorInstance<AreaOfEffectPowerBehavior> {
    public Instance(AreaOfEffectPowerBehavior behavior, Player player) {
      super(behavior, player);
    }

    @Override
    public void add() {}

    @Override
    public void remove() {}
  }
}
