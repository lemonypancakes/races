package me.lemonypancakes.races.action;

import com.google.gson.JsonObject;

import java.util.function.Consumer;

public abstract class Action<T> implements Consumer<T> {
    private final JsonObject json;

    public Action(JsonObject json) {
        this.json = json;
    }

    public abstract void accept(T t);

    public final JsonObject getJson() {
        return json;
    }
}
