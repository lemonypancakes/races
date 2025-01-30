package me.lemonypancakes.races.condition;

import com.google.gson.JsonObject;
import java.util.function.Function;
import me.lemonypancakes.races.serialization.DataContainer;
import me.lemonypancakes.races.serialization.DataSchema;

public record ConditionFactory<T>(
    DataSchema schema, Function<DataContainer, Condition<T>> constructor) {
  public Condition<T> create(final DataContainer container) {
    return this.constructor.apply(container);
  }

  public Condition<T> create(final JsonObject json) {
    return create(this.schema.read(json));
  }
}
