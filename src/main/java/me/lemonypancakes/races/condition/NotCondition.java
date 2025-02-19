package me.lemonypancakes.races.condition;

import java.util.Collections;
import java.util.List;
import me.lemonypancakes.races.serialization.DataSchema;
import me.lemonypancakes.races.serialization.DataType;
import me.lemonypancakes.races.serialization.DataTypes;

public final class NotCondition<T> extends Condition<T> {
  private final List<Condition<T>> conditions;

  public NotCondition(List<Condition<T>> conditions) {
    this.conditions = conditions;
  }

  public static <T> ConditionFactory<T> getFactory(DataType<T> type) {
    return new ConditionFactory<>(
        new DataSchema().add("conditions", DataTypes.listOf(type)),
        container -> new NotCondition<>(container.get("conditions")));
  }

  public List<Condition<T>> getConditions() {
    return Collections.unmodifiableList(conditions);
  }

  @Override
  public boolean test(T t) {
    return conditions.stream().noneMatch(condition -> condition.test(t));
  }
}
