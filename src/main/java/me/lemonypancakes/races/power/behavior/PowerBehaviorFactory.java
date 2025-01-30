package me.lemonypancakes.races.power.behavior;

import com.google.gson.JsonObject;
import java.util.function.Function;
import me.lemonypancakes.races.serialization.DataContainer;
import me.lemonypancakes.races.serialization.DataSchema;

public record PowerBehaviorFactory<T extends PowerBehavior<T>>(
    DataSchema schema, Function<DataContainer, T> constructor) {
  public PowerBehavior<T> create(final DataContainer container) {
    return this.constructor.apply(container);
  }

  public PowerBehavior<T> create(final JsonObject json) {
    return create(this.schema.read(json));
  }
}
