package me.lemonypancakes.races.condition;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Predicate;

public abstract class Condition<T> implements Predicate<T> {
  @Override
  public boolean test(@NotNull T t) {
    Objects.requireNonNull(t, "t cannot be null");
    return onTest(t);
  }

  protected abstract boolean onTest(@NotNull T t);
}
