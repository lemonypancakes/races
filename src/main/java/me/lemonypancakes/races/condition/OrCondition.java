package me.lemonypancakes.races.condition;

import java.util.List;

public final class OrCondition<T> extends Condition<T> {
    private final List<Condition<T>> conditions;

    public OrCondition(List<Condition<T>> conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean test(T t) {
        return conditions.stream().anyMatch(condition -> condition.test(t));
    }
}
