package me.lemonypancakes.races.power.behavior;

import me.lemonypancakes.races.action.Action;
import me.lemonypancakes.races.condition.Condition;
import me.lemonypancakes.races.serialization.Data;
import me.lemonypancakes.races.serialization.DataType;
import org.bukkit.entity.Player;

public class OverTimePowerBehavior extends PowerBehavior<OverTimePowerBehavior> {
  public static final PowerBehaviorFactory<OverTimePowerBehavior> FACTORY;

  static {
    FACTORY =
        new PowerBehaviorFactory<>(
            new Data()
                .add("condition", DataType.PLAYER_CONDITION)
                .add("action", DataType.PLAYER_ACTION)
                .add("interval", DataType.INTEGER),
            data ->
                new OverTimePowerBehavior(
                    data.get("condition"), data.get("action"), data.get("interval")));
  }

  private final Condition<Player> condition;
  private final Action<Player> action;
  private final int interval;

  public OverTimePowerBehavior(Condition<Player> condition, Action<Player> action, int interval) {
    this.condition = condition;
    this.action = action;
    this.interval = interval;
  }

  @Override
  public PowerBehaviorInstance<OverTimePowerBehavior> apply(Player player) {
    return new Instance(this, player);
  }

  public static class Instance extends PowerBehaviorInstance<OverTimePowerBehavior> {
    public Instance(OverTimePowerBehavior behavior, Player player) {
      super(behavior, player);
    }

    @Override
    public void tick() {
      if (!behavior.condition.test(player)) return;
      if (behavior.interval % player.getTicksLived() != 0) {}
    }
  }
}
