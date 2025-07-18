package me.lemonypancakes.races.serialization;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public interface Serializable {
  @NotNull
  JsonObject serialize();
}
