package me.lemonypancakes.races.action;

import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public abstract class Action<T> implements Consumer<T> {
  public abstract void accept(@NotNull T t);
}
