package me.lemonypancakes.races.storage;

import java.util.UUID;
import me.lemonypancakes.races.player.PlayerData;

public class MySQLStorage implements Storage {
  @Override
  public PlayerData getPlayerData(UUID uuid) {
    return null;
  }

  @Override
  public void savePlayerData(PlayerData data) {}

  @Override
  public void removePlayerData(UUID uuid) {}
}
