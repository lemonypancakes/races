package me.lemonypancakes.races.action;

import java.util.Objects;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public abstract class Action<T> implements Consumer<T> {
  @Override
  public void accept(@NotNull T t) {
    Objects.requireNonNull(t, "t cannot be null");
    onAccept(t);
  }

  protected abstract void onAccept(@NotNull T t);
}
