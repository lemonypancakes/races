package me.lemonypancakes.races.serialization;

import java.util.HashMap;
import java.util.Map;
import me.lemonypancakes.races.util.Unchecked;

public final class DataContainer {
  private final Map<String, Object> data;

  public DataContainer() {
    this.data = new HashMap<>();
  }

  public <T> T get(final String key) {
    if (!this.data.containsKey(key)) throw new IllegalArgumentException("No value for key " + key);
    return Unchecked.cast(this.data.get(key));
  }

  public void set(final String key, final Object value) {
    this.data.put(key, value);
  }
}
