package me.lemonypancakes.races.condition;

import org.bukkit.entity.Entity;

public class TestCondition extends Condition<Entity> {
    @Override
    public boolean test(Entity entity) {
        return false;
    }
}
