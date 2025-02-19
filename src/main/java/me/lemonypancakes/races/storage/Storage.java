package me.lemonypancakes.races.storage;

import java.util.UUID;
import me.lemonypancakes.races.user.UserData;

public interface Storage {
  UserData getPlayerData(UUID uuid);

  void savePlayerData(UserData data);

  void removePlayerData(UUID uuid);
}
