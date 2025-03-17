package me.lemonypancakes.races.power.behavior;

import me.lemonypancakes.races.serialization.DataSchema;
import me.lemonypancakes.races.serialization.DataTypes;
import me.lemonypancakes.races.util.AttributedAttributeModifier;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class AttributePowerBehavior extends PowerBehavior<AttributePowerBehavior> {
  public static final PowerBehaviorFactory<AttributePowerBehavior> FACTORY;

  static {
    FACTORY =
        new PowerBehaviorFactory<>(
            new DataSchema()
                .add("modifiers", DataTypes.listOf(DataTypes.ATTRIBUTED_ATTRIBUTE_MODIFIER)),
            container -> new AttributePowerBehavior(container.get("modifiers")));
  }

  private final List<AttributedAttributeModifier> modifiers;

  public AttributePowerBehavior(List<AttributedAttributeModifier> modifiers) {
    this.modifiers = modifiers;
  }

  public List<AttributedAttributeModifier> modifiers() {
    return modifiers;
  }

  @Override
  @NotNull
  public PowerBehaviorInstance<AttributePowerBehavior> apply(@NotNull Player player) {
    return new Instance(this, player);
  }

  public static final class Instance extends PowerBehaviorInstance<AttributePowerBehavior> {
    public Instance(AttributePowerBehavior behavior, Player player) {
      super(behavior, player);
    }

    @Override
    protected void onAdd() {
      behavior.modifiers.forEach(
          modifier -> {
            AttributeInstance attribute = player.getAttribute(modifier.attribute());

            if (attribute == null) return;
            attribute.addModifier(modifier.modifier());
          });
    }

    @Override
    protected void onRemove() {
      behavior.modifiers.forEach(
          modifier -> {
            AttributeInstance attribute = player.getAttribute(modifier.attribute());

            if (attribute == null) return;
            attribute.removeModifier(modifier.modifier());
          });
    }
  }
}
