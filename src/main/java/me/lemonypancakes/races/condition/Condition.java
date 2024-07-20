package me.lemonypancakes.races.condition;

import com.google.gson.JsonObject;

import java.util.function.Predicate;

public abstract class Condition<T> implements Predicate<T> {
    private final JsonObject json;

    public Condition(JsonObject json) {
        this.json = json;
    }

    public final JsonObject getJson() {
        return json;
    }

    public abstract boolean test(T t);
}
