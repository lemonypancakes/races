package me.lemonypancakes.races.action;

import com.google.gson.JsonObject;
import me.lemonypancakes.races.serialization.DataContainer;
import me.lemonypancakes.races.serialization.DataSchema;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

public record ActionFactory<T>(
    @NotNull DataSchema schema, @NotNull Function<DataContainer, Action<T>> constructor) {
  public ActionFactory {
    Objects.requireNonNull(schema, "schema cannot be null");
    Objects.requireNonNull(constructor, "constructor cannot be null");
  }

  @NotNull
  public Action<T> create(@NotNull JsonObject object) {
    Objects.requireNonNull(object, "object cannot be null");
    return create(schema.read(object));
  }

  @NotNull
  public Action<T> create(@NotNull DataContainer container) {
    Objects.requireNonNull(container, "container cannot be null");
    return constructor.apply(container);
  }
}
