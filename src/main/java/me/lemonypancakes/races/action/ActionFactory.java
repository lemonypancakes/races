package me.lemonypancakes.races.action;

import com.google.gson.JsonObject;
import java.util.function.Function;
import me.lemonypancakes.races.serialization.DataContainer;
import me.lemonypancakes.races.serialization.DataSchema;

public record ActionFactory<T>(DataSchema schema, Function<DataContainer, Action<T>> constructor) {
  public Action<T> create(DataContainer container) {
    return constructor.apply(container);
  }

  public Action<T> create(JsonObject json) {
    return create(schema.read(json));
  }
}
