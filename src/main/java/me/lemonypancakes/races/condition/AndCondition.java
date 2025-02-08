package me.lemonypancakes.races.condition;

import java.util.List;

public final class AndCondition<T> extends Condition<T> {
    private final List<Condition<T>> conditions;

    public AndCondition(List<Condition<T>> conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean test(T t) {
        return conditions.stream().allMatch(condition -> condition.test(t));
    }
}
