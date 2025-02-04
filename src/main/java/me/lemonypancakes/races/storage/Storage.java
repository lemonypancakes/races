package me.lemonypancakes.races.storage;

import java.util.UUID;
import me.lemonypancakes.races.RacesPlayerData;

public interface Storage {
  RacesPlayerData getPlayerData(UUID uuid);

  void savePlayerData(RacesPlayerData data);

  void removePlayerData(UUID uuid);
}
