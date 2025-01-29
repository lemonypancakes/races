package me.lemonypancakes.races.condition;

import java.util.function.Predicate;

public abstract class Condition<T> implements Predicate<T> {
    public abstract boolean test(T t);
}
