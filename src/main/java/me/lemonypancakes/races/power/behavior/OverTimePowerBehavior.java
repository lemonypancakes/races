package me.lemonypancakes.races.power.behavior;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

public class OverTimePowerBehavior extends PowerBehavior<OverTimePowerBehavior> {
    public OverTimePowerBehavior(JsonObject data) {
        super(data);
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
            player.setFreezeTicks(player.getFreezeTicks() + 1);
        }
    }
}
