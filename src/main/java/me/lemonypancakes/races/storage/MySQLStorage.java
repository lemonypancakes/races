package me.lemonypancakes.races.storage;

import java.util.UUID;
import me.lemonypancakes.races.RacesPlayerData;

public class MySQLStorage implements Storage {
  @Override
  public RacesPlayerData getPlayerData(UUID uuid) {
    return null;
  }

  @Override
  public void savePlayerData(RacesPlayerData data) {}

  @Override
  public void removePlayerData(UUID uuid) {}
}
