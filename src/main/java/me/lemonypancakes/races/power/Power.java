package me.lemonypancakes.races.power;

import com.google.gson.JsonObject;
import me.lemonypancakes.races.condition.Condition;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class Power implements Keyed {
    private final NamespacedKey key;
    private final JsonObject json;
    private final String name;
    private final String displayName;
    private final String description;
    private final ItemStack icon;
    private final int impact;
    private final int order;
    protected final Condition<Entity> condition;

    public Power(NamespacedKey key, JsonObject json) {
        this.key = Objects.requireNonNull(key, "key must not be null");
        this.json = Objects.requireNonNull(json, "json must not be null");
        this.name = json.has("name") ? json.get("name").getAsString() : null;
        this.displayName = json.has("displayName") ? json.get("displayName").getAsString() : null;
        this.description = json.has("description") ? json.get("description").getAsString() : null;
        this.icon =  json.has("icon") ? Bukkit.getItemFactory().createItemStack(json.get("icon").getAsString()) : null;
        this.impact = json.has("impact") ? json.get("impact").getAsInt() : 0;
        this.order = json.has("order") ? json.get("order").getAsInt() : 0;
        this.condition = new Condition<>(json) {
            @Override
            public boolean test(Entity entity) {
                return entity.getLocation().getY() > 150;
            }
        };
    }

    @Nonnull
    @Override
    public NamespacedKey getKey() {
        return key;
    }

    public JsonObject getJson() {
        return json.deepCopy();
    }

    public final String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public final String getDescription() {
        return description;
    }

    public final ItemStack getIcon() {
        return icon;
    }

    public final int getImpact() {
        return impact;
    }

    public final int getOrder() {
        return order;
    }

    public final PowerInstance<?> apply(Player player) {
        return Objects.requireNonNull(onApply(player));
    }

    protected abstract PowerInstance<?> onApply(Player player);

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Power power)) return false;

        return key.equals(power.key);
    }

    @Override
    public final int hashCode() {
        return key.hashCode();
    }

    @Override
    public final String toString() {
        return key.toString();
    }
}
