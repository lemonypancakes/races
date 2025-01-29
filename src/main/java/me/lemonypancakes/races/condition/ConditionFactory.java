package me.lemonypancakes.races.condition;

import com.google.gson.JsonObject;
import java.util.function.Function;
import me.lemonypancakes.races.serialization.Data;
import me.lemonypancakes.races.serialization.DataInstance;

public record ConditionFactory<T>(Data data, Function<DataInstance, Condition<T>> constructor) {
  public Condition<T> create(DataInstance data) {
    return constructor.apply(data);
  }

  public Condition<T> create(JsonObject data) {
    return create(this.data.read(data));
  }
}
