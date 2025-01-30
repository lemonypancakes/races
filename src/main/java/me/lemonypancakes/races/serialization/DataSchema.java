package me.lemonypancakes.races.serialization;

import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DataSchema {
  private final Map<String, DataField<?>> dataFields;

  public DataSchema() {
    dataFields = new HashMap<>();
  }

  public <T> DataSchema add(String key, DataField<T> field) {
    dataFields.put(key, field);
    return this;
  }

  public <T> DataSchema add(String key, DataType<T> dataType) {
    add(key, new DataField<>(dataType));
    return this;
  }

  public <T> DataSchema add(String key, DataType<T> dataType, T defaultValue) {
    add(key, new DataField<>(dataType, defaultValue));
    return this;
  }

  public <T> DataSchema add(
      String key, DataType<T> dataType, Function<DataContainer, T> defaultFunction) {
    add(key, new DataField<>(dataType, defaultFunction));
    return this;
  }

  public DataField<?> getField(String key) {
    if (!dataFields.containsKey(key)) throw new IllegalArgumentException("No field for key " + key);
    return dataFields.get(key);
  }

  public DataContainer read(JsonObject json) {
    DataContainer container = new DataContainer();

    dataFields.forEach(
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
