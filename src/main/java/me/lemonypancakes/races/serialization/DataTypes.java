package me.lemonypancakes.races.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.lemonypancakes.races.action.Action;
import me.lemonypancakes.races.action.ActionType;
import me.lemonypancakes.races.action.ActionTypes;
import me.lemonypancakes.races.condition.Condition;
import me.lemonypancakes.races.condition.ConditionType;
import me.lemonypancakes.races.condition.ConditionTypes;
import me.lemonypancakes.races.power.behavior.PowerBehavior;
import me.lemonypancakes.races.power.behavior.PowerBehaviorType;
import me.lemonypancakes.races.power.behavior.PowerBehaviorTypes;
import me.lemonypancakes.races.util.AttributedAttributeModifier;
import me.lemonypancakes.races.util.BiEntity;
import me.lemonypancakes.races.util.Unchecked;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public final class DataTypes {
  public static final DataType<String> STRING;
  public static final DataType<Integer> INTEGER;
  public static final DataType<Double> DOUBLE;
  public static final DataType<Boolean> BOOLEAN;

  public static final DataType<Action<Entity>> ENTITY_ACTION;
  public static final DataType<Action<Block>> BLOCK_ACTION;
  public static final DataType<Action<BiEntity>> BI_ENTITY_ACTION;
  public static final DataType<Action<ItemStack>> ITEM_ACTION;

  public static final DataType<Condition<Entity>> ENTITY_CONDITION;
  public static final DataType<Condition<Block>> BLOCK_CONDITION;
  public static final DataType<Condition<BiEntity>> BI_ENTITY_CONDITION;
  public static final DataType<Condition<ItemStack>> ITEM_CONDITION;

  public static final DataType<PowerBehavior<?>> POWER_BEHAVIOR;
  public static final DataType<AttributedAttributeModifier> ATTRIBUTED_ATTRIBUTE_MODIFIER;

  static {
    STRING = new DataType<>(String.class, JsonElement::getAsString);
    INTEGER = new DataType<>(Integer.class, JsonElement::getAsInt);
    DOUBLE = new DataType<>(Double.class, JsonElement::getAsDouble);
    BOOLEAN = new DataType<>(Boolean.class, JsonElement::getAsBoolean);

    ENTITY_ACTION = action(Entity.class);
    BLOCK_ACTION = action(Block.class);
    BI_ENTITY_ACTION = action(BiEntity.class);
    ITEM_ACTION = action(ItemStack.class);

    ENTITY_CONDITION = condition(Entity.class);
    BLOCK_CONDITION = condition(Block.class);
    BI_ENTITY_CONDITION = condition(BiEntity.class);
    ITEM_CONDITION = condition(ItemStack.class);

    POWER_BEHAVIOR = powerBehavior();
    ATTRIBUTED_ATTRIBUTE_MODIFIER = attributedAttributeModifier();
  }

  public static <T> DataType<List<T>> listOf(DataType<T> type) {
    return new DataType<>(
        Unchecked.castClass(List.class),
        element -> {
          if (!element.isJsonArray()) return null;
          return element.getAsJsonArray().asList().stream().map(type::read).toList();
        });
  }

  public static <T> DataType<T[]> arrayOf(DataType<T> type) {
    return new DataType<>(
        Unchecked.castClass(Object[].class),
        element -> {
          if (!element.isJsonArray()) return null;
          return Unchecked.cast(
              element.getAsJsonArray().asList().stream()
                  .map(type::read)
                  .toArray(typeClass -> new Object[0]));
        });
  }

  public static <T> DataType<Action<T>> action(Class<T> typeClass) {
    return new DataType<>(
        Unchecked.castClass(Action.class),
        element -> {
          if (!element.isJsonObject()) return null;
          JsonObject object = element.getAsJsonObject();
          NamespacedKey key = NamespacedKey.fromString(object.get("type").getAsString());

          if (key == null) return null;
          ActionType<T> type = ActionTypes.get(typeClass, key);

          if (type == null) return null;
          return type.factory().create(object);
        });
  }

  public static <T> DataType<Condition<T>> condition(Class<T> typeClass) {
    return new DataType<>(
        Unchecked.castClass(Condition.class),
        element -> {
          if (!element.isJsonObject()) return null;
          JsonObject object = element.getAsJsonObject();
          NamespacedKey key = NamespacedKey.fromString(object.get("type").getAsString());

          if (key == null) return null;
          ConditionType<T> type = ConditionTypes.get(typeClass, key);

          if (type == null) return null;
          return type.factory().create(object);
        });
  }

  private static DataType<PowerBehavior<?>> powerBehavior() {
    return new DataType<>(
        Unchecked.castClass(PowerBehavior.class),
        element -> {
          if (!element.isJsonObject()) return null;
          JsonObject object = element.getAsJsonObject();
          NamespacedKey key = NamespacedKey.fromString(object.get("type").getAsString());

          if (key == null) return null;
          PowerBehaviorType<?> type = PowerBehaviorTypes.get(key);

          if (type == null) return null;
          return type.factory().create(object);
        });
  }

  private static DataType<AttributedAttributeModifier> attributedAttributeModifier() {
    return new DataType<>(
        AttributedAttributeModifier.class,
        element -> {
          if (!element.isJsonObject()) return null;
          JsonObject object = element.getAsJsonObject();

          if (!object.has("attribute")) return null;
          NamespacedKey key = NamespacedKey.fromString(object.get("type").getAsString());

          if (key == null) return null;
          Attribute attribute = Registry.ATTRIBUTE.get(key);

          if (attribute == null) return null;
          key = new NamespacedKey("races", "attribute");

          if (object.has("key")) key = NamespacedKey.fromString(object.get("key").getAsString());
          if (key == null) return null;

          return new AttributedAttributeModifier(
              attribute,
              new AttributeModifier(
                  key,
                  object.has("amount") ? object.get("amount").getAsDouble() : 0,
                  object.has("operation")
                      ? AttributeModifier.Operation.valueOf(object.get("operation").getAsString())
                      : AttributeModifier.Operation.ADD_NUMBER));
        });
  }

  private DataTypes() {}
}
