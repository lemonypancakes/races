package me.lemonypancakes.races.serialization;

import com.google.gson.JsonElement;
import java.util.function.Function;

public record DataType<T>(Class<T> dataClass, Function<JsonElement, T> reader) {
  public T read(JsonElement element) {
    return reader.apply(element);
  }
}
