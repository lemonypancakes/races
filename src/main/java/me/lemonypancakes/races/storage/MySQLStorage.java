package me.lemonypancakes.races.storage;

import me.lemonypancakes.races.player.PlayerData;

import java.util.UUID;

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
