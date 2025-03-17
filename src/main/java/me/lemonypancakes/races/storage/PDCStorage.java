package me.lemonypancakes.races.storage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.papermc.paper.persistence.PersistentDataContainerView;
import me.lemonypancakes.races.Races;
import me.lemonypancakes.races.user.UserData;
import org.bukkit.Bukkit;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class PDCStorage implements Storage {
  private static final Gson GSON = new Gson();

  @Override
  public UserData getPlayerData(UUID uuid) {
    PersistentDataContainerView view = Bukkit.getOfflinePlayer(uuid).getPersistentDataContainer();
    String data = view.get(Races.namespace("data"), PersistentDataType.STRING);

    if (data == null) return null;
    JsonObject object = GSON.fromJson(data, JsonObject.class);
    return null;
  }

  @Override
  public void savePlayerData(UserData data) {}

  @Override
  public void removePlayerData(UUID uuid) {}
}
