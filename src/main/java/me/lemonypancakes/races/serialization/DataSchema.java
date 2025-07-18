package me.lemonypancakes.races.serialization;

import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DataSchema {
  private final Map<String, DataEntry<?>> entries = new HashMap<>();

  public <T> DataSchema add(String key, DataEntry<T> entry) {
    entries.put(key, entry);
    return this;
  }

  public <T> DataSchema add(String key, DataType<T> dataType) {
    add(key, new DataEntry<>(dataType));
    return this;
  }

  public <T> DataSchema add(String key, DataType<T> dataType, T defaultValue) {
    add(key, new DataEntry<>(dataType, defaultValue));
    return this;
  }

  public <T> DataSchema add(
      String key, DataType<T> dataType, Function<DataContainer, T> defaultFunction) {
    add(key, new DataEntry<>(dataType, defaultFunction));
    return this;
  }

  public DataEntry<?> getEntry(String key) {
    if (!entries.containsKey(key)) throw new IllegalArgumentException("No entry for key " + key);
    return entries.get(key);
  }

  public DataContainer read(JsonObject object) {
    DataContainer container = new DataContainer();

    entries.forEach(
        (key, entry) -> {
          if (object.has(key)) {
            container.set(key, entry.getType().read(container.get(key)));
          } else if (entry.hasDefault()) {
            container.set(key, entry.getDefault(container));
          } else {
            throw new IllegalArgumentException("No value for key " + key);
          }
        });
    return container;
  }
}
