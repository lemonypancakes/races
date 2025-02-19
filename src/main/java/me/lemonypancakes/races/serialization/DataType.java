package me.lemonypancakes.races.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.function.Function;
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

public record DataType<T>(Class<T> dataClass, Function<JsonElement, T> reader) {
  public T read(JsonElement element) {
    return reader.apply(element);
  }
}
