package me.lemonypancakes.races.power.behavior;

import com.google.gson.JsonObject;
import java.util.function.Function;
import me.lemonypancakes.races.serialization.Data;
import me.lemonypancakes.races.serialization.DataInstance;

public record PowerBehaviorFactory<T extends PowerBehavior<T>>(
    Data data, Function<DataInstance, T> constructor) {
  public PowerBehavior<T> create(DataInstance data) {
    return constructor.apply(data);
  }

  public PowerBehavior<T> create(JsonObject data) {
    return create(this.data.read(data));
  }
}
