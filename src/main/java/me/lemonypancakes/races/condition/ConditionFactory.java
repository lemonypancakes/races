package me.lemonypancakes.races.condition;

import com.google.gson.JsonObject;
import java.util.function.Function;
import me.lemonypancakes.races.serialization.DataContainer;
import me.lemonypancakes.races.serialization.DataSchema;

public record ConditionFactory<T>(
    DataSchema schema, Function<DataContainer, Condition<T>> constructor) {
  public Condition<T> create(DataContainer container) {
    return constructor.apply(container);
  }

  public Condition<T> create(JsonObject json) {
    return create(schema.read(json));
  }
}
