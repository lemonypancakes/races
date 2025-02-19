package me.lemonypancakes.races.action;

import com.google.gson.JsonObject;
import java.util.Objects;
import java.util.function.Function;
import me.lemonypancakes.races.serialization.DataContainer;
import me.lemonypancakes.races.serialization.DataSchema;

public record ActionFactory<T>(DataSchema schema, Function<DataContainer, Action<T>> constructor) {
  public ActionFactory {
    Objects.requireNonNull(schema, "schema cannot be null");
    Objects.requireNonNull(constructor, "constructor cannot be null");
  }

  public Action<T> create(DataContainer container) {
    Objects.requireNonNull(container, "container cannot be null");
    return constructor.apply(container);
  }

  public Action<T> create(JsonObject object) {
    Objects.requireNonNull(object, "object cannot be null");
    return create(schema.read(object));
  }
}
