package me.lemonypancakes.races.power.behavior;

import com.google.gson.JsonObject;
import me.lemonypancakes.races.serialization.DataContainer;
import me.lemonypancakes.races.serialization.DataSchema;

import java.util.function.Function;

public record PowerBehaviorFactory<T extends PowerBehavior<T>>(
    DataSchema schema, Function<DataContainer, T> constructor) {
  public PowerBehavior<T> create(DataContainer container) {
    return constructor.apply(container);
  }

  public PowerBehavior<T> create(JsonObject json) {
    return create(schema.read(json));
  }
}
