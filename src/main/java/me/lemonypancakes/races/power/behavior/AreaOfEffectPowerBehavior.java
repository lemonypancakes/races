package me.lemonypancakes.races.power.behavior;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class AreaOfEffectPowerBehavior extends PowerBehavior<AreaOfEffectPowerBehavior> {
  @Override
  public @NotNull PowerBehaviorInstance<AreaOfEffectPowerBehavior> apply(@NotNull Player player) {
    return new Instance(this, player);
  }

  public static final class Instance extends PowerBehaviorInstance<AreaOfEffectPowerBehavior> {
    public Instance(AreaOfEffectPowerBehavior behavior, Player player) {
      super(behavior, player);
    }

    @Override
    protected void onAdd() {}

    @Override
    protected void onRemove() {}
  }
}
