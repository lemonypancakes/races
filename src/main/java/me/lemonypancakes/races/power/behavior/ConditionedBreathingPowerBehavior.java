package me.lemonypancakes.races.power.behavior;

import me.lemonypancakes.races.condition.Condition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ConditionedBreathingPowerBehavior
    extends PowerBehavior<ConditionedBreathingPowerBehavior> {
  private final Condition<Entity> condition;

  public ConditionedBreathingPowerBehavior(Condition<Entity> condition) {
    this.condition = condition;
  }

  public Condition<Entity> getCondition() {
    return condition;
  }

  @Override
  public @NotNull PowerBehaviorInstance<ConditionedBreathingPowerBehavior> apply(
      @NotNull Player player) {
    return new Instance(this, player);
  }

  public static class Instance extends PowerBehaviorInstance<ConditionedBreathingPowerBehavior> {
    public Instance(ConditionedBreathingPowerBehavior behavior, Player player) {
      super(behavior, player);
    }

    @Override
    protected void onAdd() {
      Location eyeLocation = player.getEyeLocation();
      Block block = eyeLocation.getBlock();
      Material material = block.getType();

      if ((material.isAir()
              || material.isSolid()
              || material == Material.LAVA
              || material == Material.LAVA_CAULDRON)
          && (!(material.createBlockData() instanceof Waterlogged)
              || !((Waterlogged) material.createBlockData()).isWaterlogged())) {
        player.setRemainingAir(Math.max(player.getRemainingAir() - 5, -27));
      } else {
        player.setRemainingAir(Math.min(player.getRemainingAir() + 5, 303));
      }
    }

    @Override
    protected void onRemove() {}

    @Override
    protected void onTick() {
      if (player.getRemainingAir() <= -27) {
        player.damage(1.0);
      }
    }
  }
}
