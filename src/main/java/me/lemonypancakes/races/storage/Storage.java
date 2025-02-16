package me.lemonypancakes.races.storage;

import java.util.UUID;
import me.lemonypancakes.races.player.PlayerData;

public interface Storage {
  PlayerData getPlayerData(UUID uuid);

  void savePlayerData(PlayerData data);

  void removePlayerData(UUID uuid);
}
