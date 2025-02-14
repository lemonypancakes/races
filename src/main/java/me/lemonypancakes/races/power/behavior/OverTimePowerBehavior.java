package me.lemonypancakes.races.power.behavior;

import me.lemonypancakes.races.action.Action;
import me.lemonypancakes.races.condition.Condition;
import me.lemonypancakes.races.serialization.DataSchema;
import me.lemonypancakes.races.serialization.DataType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public final class OverTimePowerBehavior extends PowerBehavior<OverTimePowerBehavior> {
  public static final PowerBehaviorFactory<OverTimePowerBehavior> FACTORY;

  static {
    FACTORY =
        new PowerBehaviorFactory<>(
            new DataSchema()
                .add("condition", DataType.ENTITY_CONDITION)
                .add("action", DataType.ENTITY_ACTION)
                .add("interval", DataType.INTEGER),
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
  public PowerBehaviorInstance<OverTimePowerBehavior> apply(Player player) {
    return new Instance(this, player);
  }

  public static final class Instance extends PowerBehaviorInstance<OverTimePowerBehavior> {
    public Instance(OverTimePowerBehavior behavior, Player player) {
      super(behavior, player);
    }

    @Override
    public void add() {}

    @Override
    public void remove() {}

    @Override
    public void tick() {
      if (player.getTicksLived() % behavior.interval != 0) return;
      if (behavior.condition == null || !behavior.condition.test(player)) return;
      if (behavior.action != null) behavior.action.accept(player);
    }
  }
}
