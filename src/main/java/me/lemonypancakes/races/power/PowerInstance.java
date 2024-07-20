package me.lemonypancakes.races.power;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public abstract class PowerInstance<T extends Power> {
    private final T power;
    private final Player player;
    private boolean active;
    private State state;

    public PowerInstance(final T power, final Player player) {
        this.power = power;
        this.player = player;
    }

    public final T getPower() {
        return power;
    }

    public final Player getPlayer() {
        return player;
    }

    public final void grant() {
        active = true;
        state = State.GRANTED;
        onGrant();
    }

    protected void onGrant() {

    }

    public final void revoke() {
        onRevoke();
    }

    protected void onRevoke() {

    }

    public final void add() {
        onAdd();
    }

    protected void onAdd() {

    }

    public final void remove() {
        onRemove();
    }

    protected void onRemove() {

    }

    public final boolean isActive() {
        return active;
    }

    public final State getState() {
        return state;
    }

    private enum Manager {
        INSTANCE;

        private final Map<Player, PowerInstance<?>> instances = new HashMap<>();

        public void add(PowerInstance<?> powerInstance) {
            instances.put(powerInstance.getPlayer(), powerInstance);
        }

        public void remove(PowerInstance<?> powerInstance) {
            instances.remove(powerInstance.getPlayer());
        }
    }

    public enum State {
        GRANTED,
        REVOKED,
        ADDED,
        REMOVED
    }
}
