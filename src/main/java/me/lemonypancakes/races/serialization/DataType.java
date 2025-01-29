package me.lemonypancakes.races.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.UUID;
import java.util.function.Function;
import me.lemonypancakes.races.action.Action;
import me.lemonypancakes.races.action.ActionType;
import me.lemonypancakes.races.condition.Condition;
import me.lemonypancakes.races.condition.ConditionType;
import me.lemonypancakes.races.power.behavior.PowerBehavior;
import me.lemonypancakes.races.power.behavior.PowerBehaviorType;
import me.lemonypancakes.races.util.AttributedAttributeModifier;
import me.lemonypancakes.races.util.Unchecked;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

public record DataType<T>(Class<T> dataClass, Function<JsonElement, T> reader) {
  public static final DataType<String> STRING;
  public static final DataType<Integer> INTEGER;
  public static final DataType<Double> DOUBLE;
  public static final DataType<Boolean> BOOLEAN;
  public static final DataType<Action<Player>> PLAYER_ACTION;
  public static final DataType<Condition<Player>> PLAYER_CONDITION;
  public static final DataType<PowerBehavior<?>> POWER_BEHAVIOR;
  public static final DataType<AttributedAttributeModifier> ATTRIBUTED_ATTRIBUTE_MODIFIER;

  static {
    STRING = new DataType<>(String.class, JsonElement::getAsString);
    INTEGER = new DataType<>(Integer.class, JsonElement::getAsInt);
    DOUBLE = new DataType<>(Double.class, JsonElement::getAsDouble);
    BOOLEAN = new DataType<>(Boolean.class, JsonElement::getAsBoolean);
    PLAYER_ACTION = action(Player.class);
    PLAYER_CONDITION = condition(Player.class);
    POWER_BEHAVIOR = powerBehavior();
    ATTRIBUTED_ATTRIBUTE_MODIFIER = attributedAttributeModifier();
  }

  private static <T> DataType<Action<T>> action(Class<T> typeClass) {
    return new DataType<>(
        Unchecked.castClass(Action.class),
        jsonElement -> {
          if (!jsonElement.isJsonObject()) return null;
          JsonObject jsonObject = jsonElement.getAsJsonObject();
          NamespacedKey key = NamespacedKey.fromString(jsonObject.get("type").getAsString());

          if (key == null) return null;
          ActionType<T> actionType = ActionType.get(typeClass, key);

          if (actionType == null) return null;
          return actionType.factory().create(jsonObject);
        });
  }

  private static <T> DataType<Condition<T>> condition(Class<T> typeClass) {
    return new DataType<>(
        Unchecked.castClass(Condition.class),
        jsonElement -> {
          if (!jsonElement.isJsonObject()) return null;
          JsonObject jsonObject = jsonElement.getAsJsonObject();
          NamespacedKey key = NamespacedKey.fromString(jsonObject.get("type").getAsString());

          if (key == null) return null;
          ConditionType<T> conditionType = ConditionType.get(typeClass, key);

          if (conditionType == null) return null;
          return conditionType.factory().create(jsonObject);
        });
  }

  private static DataType<PowerBehavior<?>> powerBehavior() {
    return new DataType<>(
        Unchecked.castClass(PowerBehavior.class),
        jsonElement -> {
          if (!jsonElement.isJsonObject()) return null;
          JsonObject jsonObject = jsonElement.getAsJsonObject();
          NamespacedKey key = NamespacedKey.fromString(jsonObject.get("type").getAsString());

          if (key == null) return null;
          PowerBehaviorType<?> powerBehaviorType = PowerBehaviorType.get(key);

          if (powerBehaviorType == null) return null;
          return powerBehaviorType.factory().create(jsonObject);
        });
  }

  private static DataType<AttributedAttributeModifier> attributedAttributeModifier() {
    return new DataType<>(
        AttributedAttributeModifier.class,
        jsonElement -> {
          if (!jsonElement.isJsonObject()) return null;
          JsonObject jsonObject = jsonElement.getAsJsonObject();

          if (!jsonObject.has("attribute")) return null;
          NamespacedKey key = NamespacedKey.fromString(jsonObject.get("type").getAsString());

          if (key == null) return null;
          Attribute attribute = Registry.ATTRIBUTE.get(key);

          if (attribute == null) return null;
          return new AttributedAttributeModifier(
              attribute,
              new AttributeModifier(
                  jsonObject.has("uuid")
                      ? UUID.fromString(jsonObject.get("uuid").getAsString())
                      : UUID.randomUUID(),
                  jsonObject.has("name") ? jsonObject.get("name").getAsString() : "",
                  jsonObject.has("amount") ? jsonObject.get("amount").getAsDouble() : 0,
                  jsonObject.has("operation")
                      ? AttributeModifier.Operation.valueOf(
                          jsonObject.get("operation").getAsString())
                      : AttributeModifier.Operation.ADD_NUMBER));
        });
  }

  public T read(JsonElement element) {
    return reader.apply(element);
  }
}
