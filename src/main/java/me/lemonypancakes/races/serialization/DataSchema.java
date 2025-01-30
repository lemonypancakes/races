package me.lemonypancakes.races.serialization;

import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DataSchema {
  private final Map<String, DataField<?>> dataFields;

  public DataSchema() {
    this.dataFields = new HashMap<>();
  }

  public <T> DataSchema add(final String key, final DataField<T> field) {
    this.dataFields.put(key, field);
    return this;
  }

  public <T> DataSchema add(final String key, final DataType<T> dataType) {
    add(key, new DataField<>(dataType));
    return this;
  }

  public <T> DataSchema add(final String key, final DataType<T> dataType, final T defaultValue) {
    add(key, new DataField<>(dataType, defaultValue));
    return this;
  }

  public <T> DataSchema add(
      final String key,
      final DataType<T> dataType,
      final Function<DataContainer, T> defaultFunction) {
    add(key, new DataField<>(dataType, defaultFunction));
    return this;
  }

  public DataField<?> getField(final String key) {
    if (!this.dataFields.containsKey(key))
      throw new IllegalArgumentException("No field for key " + key);
    return this.dataFields.get(key);
  }

  public DataContainer read(final JsonObject json) {
    final DataContainer container = new DataContainer();

    this.dataFields.forEach(
        (key, field) -> {
          if (json.has(key)) {
            container.set(key, field.getDataType().read(container.get(key)));
          } else if (field.hasDefault()) {
            container.set(key, field.getDefault(container));
          } else {
            throw new IllegalArgumentException("No value for key " + key);
          }
        });
    return container;
  }
}
