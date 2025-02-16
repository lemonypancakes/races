package me.lemonypancakes.races.condition;

import me.lemonypancakes.races.serialization.DataSchema;
import me.lemonypancakes.races.serialization.DataType;

import java.util.Collections;
import java.util.List;

public final class OrCondition<T> extends Condition<T> {
  private final List<Condition<T>> conditions;

  public OrCondition(List<Condition<T>> conditions) {
    this.conditions = conditions;
  }

  public static <T> ConditionFactory<T> getFactory(DataType<T> type) {
    return new ConditionFactory<>(
        new DataSchema().add("conditions", DataType.listOf(type)),
        container -> new OrCondition<>(container.get("conditions")));
  }

  public List<Condition<T>> getConditions() {
    return Collections.unmodifiableList(conditions);
  }

  @Override
  public boolean test(T t) {
    return conditions.stream().anyMatch(condition -> condition.test(t));
  }
}
