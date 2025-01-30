package me.lemonypancakes.races.power.behavior;

import me.lemonypancakes.races.serialization.DataSchema;
import me.lemonypancakes.races.serialization.DataType;
import me.lemonypancakes.races.util.AttributedAttributeModifier;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class AttributePowerBehavior extends PowerBehavior<AttributePowerBehavior> {
  public static final PowerBehaviorFactory<AttributePowerBehavior> FACTORY;

  static {
    FACTORY =
        new PowerBehaviorFactory<>(
            new DataSchema().add("modifier", DataType.ATTRIBUTED_ATTRIBUTE_MODIFIER),
            container -> new AttributePowerBehavior(container.get("modifier")));
  }

  private final AttributedAttributeModifier modifier;

  public AttributePowerBehavior(final AttributedAttributeModifier modifier) {
    this.modifier = modifier;
  }

  public AttributedAttributeModifier getModifier() {
    return this.modifier;
  }

  @Override
  public PowerBehaviorInstance<AttributePowerBehavior> apply(final Player player) {
    return new Instance(this, player);
  }

  public static final class Instance extends PowerBehaviorInstance<AttributePowerBehavior> {
    public Instance(final AttributePowerBehavior behavior, final Player player) {
      super(behavior, player);
    }

    @Override
    public void add() {
      final AttributeInstance attribute =
          this.player.getAttribute(this.behavior.modifier.attribute());

      if (attribute == null) return;
      attribute.addModifier(this.behavior.modifier.modifier());
    }

    @Override
    public void remove() {
      final AttributeInstance attribute =
          this.player.getAttribute(this.behavior.modifier.attribute());

      if (attribute == null) return;
      attribute.removeModifier(this.behavior.modifier.modifier());
    }
  }
}
