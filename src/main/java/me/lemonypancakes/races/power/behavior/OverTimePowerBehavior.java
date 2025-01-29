package me.lemonypancakes.races.power.behavior;

import me.lemonypancakes.races.action.Action;
import me.lemonypancakes.races.condition.Condition;
import org.bukkit.entity.Player;

public class OverTimePowerBehavior extends PowerBehavior<OverTimePowerBehavior> {
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
            if (behavior.interval == 0 || player.getTicksLived() % behavior.interval == 0) {
            }
        }
    }
}
