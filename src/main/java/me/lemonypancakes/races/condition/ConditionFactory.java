package me.lemonypancakes.races.condition;

import com.google.gson.JsonObject;

public interface ConditionFactory<T> {
    Condition<T> create(JsonObject json);
}
