package me.lemonypancakes.races.storage;

import me.lemonypancakes.races.player.PlayerData;

import java.util.UUID;

public interface Storage {
  PlayerData getPlayerData(UUID uuid);

  void savePlayerData(PlayerData data);

  void removePlayerData(UUID uuid);
}
