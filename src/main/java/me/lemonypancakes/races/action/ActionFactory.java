package me.lemonypancakes.races.action;

import com.google.gson.JsonObject;

@FunctionalInterface
public interface ActionFactory<T> {
    Action<T> create(JsonObject json);
}
