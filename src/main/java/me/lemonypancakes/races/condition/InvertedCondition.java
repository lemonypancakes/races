package me.lemonypancakes.races.condition;

import me.lemonypancakes.races.serialization.DataSchema;
import me.lemonypancakes.races.serialization.DataType;

import java.util.Collections;
import java.util.List;

public final class InvertedCondition<T> extends Condition<T> {
  private final List<Condition<T>> conditions;

  public InvertedCondition(List<Condition<T>> conditions) {
    this.conditions = conditions;
  }

  public static <T> ConditionFactory<T> getFactory(DataType<T> type) {
    return new ConditionFactory<>(
        new DataSchema().add("conditions", DataType.listOf(type)),
        container -> new InvertedCondition<>(container.get("conditions")));
  }

  public List<Condition<T>> getConditions() {
    return Collections.unmodifiableList(conditions);
  }

  @Override
  public boolean test(T t) {
    return conditions.stream().noneMatch(condition -> condition.test(t));
  }
}
