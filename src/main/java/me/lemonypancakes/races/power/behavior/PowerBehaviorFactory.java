package me.lemonypancakes.races.power.behavior;

import com.google.gson.JsonObject;
import java.util.Objects;
import java.util.function.Function;
import me.lemonypancakes.races.serialization.DataContainer;
import me.lemonypancakes.races.serialization.DataSchema;

public record PowerBehaviorFactory<T extends PowerBehavior<T>>(
    DataSchema schema, Function<DataContainer, T> constructor) {
  public PowerBehaviorFactory {
    Objects.requireNonNull(schema, "schema cannot be null");
    Objects.requireNonNull(constructor, "constructor cannot be null");
  }

  public PowerBehavior<T> create(DataContainer container) {
    Objects.requireNonNull(container, "container cannot be null");
    return constructor.apply(container);
  }

  public PowerBehavior<T> create(JsonObject object) {
    Objects.requireNonNull(object, "object cannot be null");
    return create(schema.read(object));
  }
}
