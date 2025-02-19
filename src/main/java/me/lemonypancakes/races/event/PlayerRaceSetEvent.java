package me.lemonypancakes.races.event;

import me.lemonypancakes.races.race.Race;
import me.lemonypancakes.races.race.RaceGroup;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerRaceSetEvent extends PlayerEvent {
  private static final HandlerList HANDLERS = new HandlerList();
  private final RaceGroup group;
  private final Race race;

  public PlayerRaceSetEvent(Player who, RaceGroup group, Race race) {
    super(who);
    this.group = group;
    this.race = race;
  }

  public RaceGroup getGroup() {
    return group;
  }

  public Race getRace() {
    return race;
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLERS;
  }
}
