package me.lemonypancakes.races.power.behavior;

import com.google.gson.JsonObject;
import me.lemonypancakes.races.serialization.DataContainer;
import me.lemonypancakes.races.serialization.DataSchema;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

public record PowerBehaviorFactory<T extends PowerBehavior<T>>(
    @NotNull DataSchema schema, @NotNull Function<DataContainer, T> constructor) {
  public PowerBehaviorFactory {
    Objects.requireNonNull(schema, "schema cannot be null");
    Objects.requireNonNull(constructor, "constructor cannot be null");
  }

  @NotNull
  public PowerBehavior<T> create(@NotNull JsonObject object) {
    Objects.requireNonNull(object, "object cannot be null");
    return create(schema.read(object));
  }

  @NotNull
  public PowerBehavior<T> create(@NotNull DataContainer container) {
    Objects.requireNonNull(container, "container cannot be null");
    return constructor.apply(container);
  }
}
