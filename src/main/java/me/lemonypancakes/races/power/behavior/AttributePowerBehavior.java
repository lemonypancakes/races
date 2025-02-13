package me.lemonypancakes.races.power.behavior;

import me.lemonypancakes.races.serialization.DataSchema;
import me.lemonypancakes.races.serialization.DataType;
import me.lemonypancakes.races.util.AttributedAttributeModifier;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public final class AttributePowerBehavior extends PowerBehavior<AttributePowerBehavior> {
  public static final PowerBehaviorFactory<AttributePowerBehavior> FACTORY;

  static {
    FACTORY =
        new PowerBehaviorFactory<>(
            new DataSchema().add("modifier", DataType.ATTRIBUTED_ATTRIBUTE_MODIFIER),
            container -> new AttributePowerBehavior(container.get("modifier")));
  }

  private final AttributedAttributeModifier modifier;

  public AttributePowerBehavior(AttributedAttributeModifier modifier) {
    this.modifier = modifier;
  }

  public AttributedAttributeModifier getModifier() {
    return modifier;
  }

  @Override
  public PowerBehaviorInstance<AttributePowerBehavior> apply(Player player) {
    return new Instance(this, player);
  }

  public static final class Instance extends PowerBehaviorInstance<AttributePowerBehavior> {
    public Instance(AttributePowerBehavior behavior, Player player) {
      super(behavior, player);
    }

    @Override
    public void add() {
      AttributeInstance attribute = player.getAttribute(behavior.modifier.attribute());

      if (attribute == null) return;
      attribute.addModifier(behavior.modifier.modifier());
    }

    @Override
    public void remove() {
      AttributeInstance attribute = player.getAttribute(behavior.modifier.attribute());

      if (attribute == null) return;
      attribute.removeModifier(behavior.modifier.modifier());
    }
  }
}
