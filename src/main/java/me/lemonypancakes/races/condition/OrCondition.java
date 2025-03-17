package me.lemonypancakes.races.condition;

import me.lemonypancakes.races.serialization.DataSchema;
import me.lemonypancakes.races.serialization.DataType;
import me.lemonypancakes.races.serialization.DataTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public final class OrCondition<T> extends Condition<T> {
  private final List<Condition<T>> conditions;

  public OrCondition(@NotNull List<Condition<T>> conditions) {
    this.conditions = conditions;
  }

  @NotNull
  public static <T> ConditionFactory<T> getFactory(@NotNull DataType<T> type) {
    return new ConditionFactory<>(
        new DataSchema().add("conditions", DataTypes.listOf(type)),
        container -> new OrCondition<>(container.get("conditions")));
  }

  @NotNull
  public List<Condition<T>> getConditions() {
    return Collections.unmodifiableList(conditions);
  }

  @Override
  public boolean onTest(@NotNull T t) {
    return conditions.stream().anyMatch(condition -> condition.test(t));
  }
}
