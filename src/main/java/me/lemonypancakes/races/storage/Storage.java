package me.lemonypancakes.races.storage;

import me.lemonypancakes.races.user.UserData;

import java.util.UUID;

public interface Storage {
  UserData getPlayerData(UUID uuid);

  void savePlayerData(UserData data);

  void removePlayerData(UUID uuid);
}
