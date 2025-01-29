package me.lemonypancakes.races.action;

import org.bukkit.entity.Entity;

public class TestAction extends Action<Entity> {
    public static final ActionFactory<Entity> FACTORY;

    static {
        FACTORY =
    }

    public TestAction(String test) {
    }

    @Override
    public void accept(Entity entity) {
        entity.setFreezeTicks(entity.getFreezeTicks() + 2);
    }


}
