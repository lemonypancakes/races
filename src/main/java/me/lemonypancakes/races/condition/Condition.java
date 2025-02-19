package me.lemonypancakes.races.condition;

import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

public abstract class Condition<T> implements Predicate<T> {
  public abstract boolean test(@NotNull T t);
}
