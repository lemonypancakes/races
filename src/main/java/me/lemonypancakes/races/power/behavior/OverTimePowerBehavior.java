package me.lemonypancakes.races.power.behavior;

import me.lemonypancakes.races.action.Action;
import me.lemonypancakes.races.condition.Condition;
import me.lemonypancakes.races.serialization.DataSchema;
import me.lemonypancakes.races.serialization.DataType;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class OverTimePowerBehavior extends PowerBehavior<OverTimePowerBehavior> {
  public static final PowerBehaviorFactory<OverTimePowerBehavior> FACTORY;

  static {
    FACTORY =
        new PowerBehaviorFactory<>(
            new DataSchema()
                .add("condition", DataType.PLAYER_CONDITION)
                .add("action", DataType.PLAYER_ACTION)
                .add("interval", DataType.INTEGER),
            container ->
                new OverTimePowerBehavior(
                    container.get("condition"),
                    container.get("action"),
                    container.get("interval")));
  }

  private final Condition<Player> condition;
  private final Action<Player> action;
  private final int interval;

  public OverTimePowerBehavior(Condition<Player> condition, Action<Player> action, int interval) {
    this.condition = condition;
    this.action = action;
    this.interval = interval;
  }

  public Condition<Player> getCondition() {
    return condition;
  }

  public Action<Player> getAction() {
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
      player.getLocation().add(0, -1, 0).getBlock().setType(Material.DIAMOND_BLOCK);
    }
  }
}
