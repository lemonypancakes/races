package me.lemonypancakes.races.action;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class Action<T> implements Consumer<T> {
  @Override
  public void accept(@NotNull T t) {
    Objects.requireNonNull(t, "t cannot be null");
    onAccept(t);
  }

  protected abstract void onAccept(@NotNull T t);
}
