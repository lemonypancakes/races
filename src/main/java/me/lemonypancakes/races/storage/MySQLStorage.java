package me.lemonypancakes.races.storage;

import me.lemonypancakes.races.user.UserData;

import java.util.UUID;

public class MySQLStorage implements Storage {
  @Override
  public UserData getPlayerData(UUID uuid) {
    return null;
  }

  @Override
  public void savePlayerData(UserData data) {}

  @Override
  public void removePlayerData(UUID uuid) {}
}
