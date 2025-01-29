package me.lemonypancakes.races.action;

import java.util.function.Consumer;

public abstract class Action<T> implements Consumer<T> {
    public abstract void accept(T t);
}
