package me.lemonypancakes.races.power.behavior;

import com.google.gson.JsonObject;

@FunctionalInterface
public interface PowerBehaviorFactory<T extends PowerBehavior<T>> {
    T create(JsonObject data);
}
