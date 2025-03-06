package me.lemonypancakes.races.power.behavior;

import me.lemonypancakes.races.action.Action;
import me.lemonypancakes.races.condition.Condition;
import me.lemonypancakes.races.serialization.DataSchema;
import me.lemonypancakes.races.serialization.DataTypes;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class OverTimePowerBehavior extends PowerBehavior<OverTimePowerBehavior> {
  public static final PowerBehaviorFactory<OverTimePowerBehavior> FACTORY;

  static {
    FACTORY =
        new PowerBehaviorFactory<>(
            new DataSchema()
                .add("condition", DataTypes.ENTITY_CONDITION)
                .add("action", DataTypes.ENTITY_ACTION)
                .add("interval", DataTypes.INTEGER),
            container ->
                new OverTimePowerBehavior(
                    container.get("condition"),
                    container.get("action"),
                    container.get("interval")));
  }

  private final Condition<Entity> condition;
  private final Action<Entity> action;
  private final int interval;

  public OverTimePowerBehavior(Condition<Entity> condition, Action<Entity> action, int interval) {
    this.condition = condition;
    this.action = action;
    this.interval = interval;
  }

  public Condition<Entity> getCondition() {
    return condition;
  }

  public Action<Entity> getAction() {
    return action;
  }

  public int getInterval() {
    return interval;
  }

  @Override
  @NotNull
  public PowerBehaviorInstance<OverTimePowerBehavior> apply(@NotNull Player player) {
    return new Instance(this, player);
  }

  public static final class Instance extends PowerBehaviorInstance<OverTimePowerBehavior> {
    public Instance(OverTimePowerBehavior behavior, Player player) {
      super(behavior, player);
    }

    @Override
    protected void onAdd() {}

    @Override
    protected void onRemove() {}

    @Override
    protected void onTick() {
      if (player.getTicksLived() % behavior.interval != 0) return;
      if (behavior.condition == null || !behavior.condition.test(player)) return;
      if (behavior.action != null) behavior.action.accept(player);
    }
  }
}
