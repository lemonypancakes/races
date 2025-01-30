package me.lemonypancakes.races.power;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.NamespacedKey;

public final class PowerRepository {
  private final Map<NamespacedKey, Power> powers;

  public PowerRepository() {
    this.powers = new HashMap<>();
  }

  public PowerRepository reload() {
    return this;
  }

  public Map<NamespacedKey, Power> getPowers() {
    return this.powers;
  }

  public Power getPower(final NamespacedKey key) {
    return this.powers.get(key);
  }
}
