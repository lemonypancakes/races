package me.lemonypancakes.races.action;

import com.google.gson.JsonObject;

public interface ActionFactory<T> {
    Action<T> create(JsonObject json);
}
