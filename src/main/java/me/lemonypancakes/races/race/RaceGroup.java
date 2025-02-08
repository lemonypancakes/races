package me.lemonypancakes.races.race;

import java.util.Collections;
import java.util.List;

public record RaceGroup(List<Race> races) {
  @Override
  public List<Race> races() {
    return Collections.unmodifiableList(races);
  }
}
