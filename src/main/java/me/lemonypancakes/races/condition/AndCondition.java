package me.lemonypancakes.races.condition;

import java.util.Collections;
import java.util.List;
import me.lemonypancakes.races.serialization.DataSchema;
import me.lemonypancakes.races.serialization.DataType;

public final class AndCondition<T> extends Condition<T> {
  private final List<Condition<T>> conditions;

  public AndCondition(List<Condition<T>> conditions) {
    this.conditions = conditions;
  }

  public static <T> ConditionFactory<T> getFactory(DataType<T> type) {
    return new ConditionFactory<>(
        new DataSchema().add("conditions", DataType.listOf(type)),
        container -> new AndCondition<>(container.get("conditions")));
  }

  public List<Condition<T>> getConditions() {
    return Collections.unmodifiableList(conditions);
  }

  @Override
  public boolean test(T t) {
    return conditions.stream().allMatch(condition -> condition.test(t));
  }
}
