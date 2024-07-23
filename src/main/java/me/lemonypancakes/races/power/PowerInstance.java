package me.lemonypancakes.races.power;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class PowerInstance<T extends Power> {
    private final T power;
    private final Player player;
    private State state;

    public PowerInstance(final T power, final Player player) {
        this.power = Objects.requireNonNull(power, "power must not be null");
        this.player = Objects.requireNonNull(player, "player must not be null");
    }

    public final T getPower() {
        return power;
    }

    public final Player getPlayer() {
        return player;
    }

    public final void grant() {
        if (isActive()) return;
        state = State.GRANTED;
        onGrant();
        Manager.INSTANCE.add(this);
    }

    protected void onGrant() {
        onAdd();
    }

    public final void revoke() {
        if (!isActive()) return;
        state = State.REVOKED;
        onRevoke();
        Manager.INSTANCE.remove(this);
    }

    protected void onRevoke() {
        onRemove();
    }

    public final void add() {
        if (isActive()) return;
        state = State.ADDED;
        onAdd();
        Manager.INSTANCE.add(this);
    }

    protected abstract void onAdd();

    public final void remove() {
        if (!isActive()) return;
        state = State.REMOVED;
        onRemove();
        Manager.INSTANCE.remove(this);
    }

    protected abstract void onRemove();

    public final boolean isActive() {
        return state == State.GRANTED || state == State.ADDED;
    }

    public final State getState() {
        return state;
    }

    public static void removeAll(Player player) {
        Manager.INSTANCE.removeAll(player);
    }

    private enum Manager {
        INSTANCE;

        private final Map<Player, Map<Power, PowerInstance<?>>> players;

        Manager() {
            players = new HashMap<>();
        }

        public void add(PowerInstance<?> powerInstance) {
            players.computeIfAbsent(powerInstance.player, k -> new HashMap<>());
            players.get(powerInstance.player).putIfAbsent(powerInstance.power, powerInstance);
        }

        public void remove(PowerInstance<?> powerInstance) {
            Map<Power, PowerInstance<?>> powerInstances = players.get(powerInstance.getPlayer());
            if (powerInstances == null) return;
            powerInstances.remove(powerInstance.power);
            if (powerInstances.isEmpty()) players.remove(powerInstance.player);
        }

        public void removeAll(Player player) {
            Map<Power, PowerInstance<?>> powerInstances = players.get(player);
            if (powerInstances == null) return;
            for (PowerInstance<?> powerInstance : powerInstances.values()) {
                powerInstance.remove();
            }
        }
    }

    public enum State {
        GRANTED,
        REVOKED,
        ADDED,
        REMOVED
    }
}
