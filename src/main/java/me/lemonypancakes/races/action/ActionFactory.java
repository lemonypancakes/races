package me.lemonypancakes.races.action;

import com.google.gson.JsonObject;
import java.util.function.Function;
import me.lemonypancakes.races.serialization.Data;
import me.lemonypancakes.races.serialization.DataInstance;

public record ActionFactory<T>(Data data, Function<DataInstance, Action<T>> constructor) {
  public Action<T> create(DataInstance data) {
    return constructor.apply(data);
  }

  public Action<T> create(JsonObject data) {
    return create(this.data.read(data));
  }
}
