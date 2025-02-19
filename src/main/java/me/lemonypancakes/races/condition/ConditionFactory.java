package me.lemonypancakes.races.condition;

import com.google.gson.JsonObject;
import java.util.Objects;
import java.util.function.Function;
import me.lemonypancakes.races.serialization.DataContainer;
import me.lemonypancakes.races.serialization.DataSchema;
import org.jetbrains.annotations.NotNull;

public record ConditionFactory<T>(
    @NotNull DataSchema schema, @NotNull Function<DataContainer, Condition<T>> constructor) {
  public ConditionFactory {
    Objects.requireNonNull(schema, "schema cannot be null");
    Objects.requireNonNull(constructor, "constructor cannot be null");
  }

  @NotNull
  public Condition<T> create(@NotNull DataContainer container) {
    Objects.requireNonNull(container, "container cannot be null");
    return constructor.apply(container);
  }

  @NotNull
  public Condition<T> create(@NotNull JsonObject object) {
    Objects.requireNonNull(object, "object cannot be null");
    return create(schema.read(object));
  }
}
