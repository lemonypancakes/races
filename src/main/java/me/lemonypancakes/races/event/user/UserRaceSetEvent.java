package me.lemonypancakes.races.event.user;

import me.lemonypancakes.races.race.Race;
import me.lemonypancakes.races.race.RaceGroup;
import me.lemonypancakes.races.user.User;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UserRaceSetEvent extends UserEvent {
  private static final HandlerList HANDLERS = new HandlerList();
  private final RaceGroup group;
  private final Race oldRace;
  private Race newRace;

  public UserRaceSetEvent(
      @NotNull User user,
      @NotNull RaceGroup group,
      @Nullable Race oldRace,
      @Nullable Race newRace) {
    super(user);
    this.group = group;
    this.oldRace = oldRace;
    this.newRace = newRace;
  }

  @NotNull
  public RaceGroup getGroup() {
    return group;
  }

  @Nullable
  public Race getOldRace() {
    return oldRace;
  }

  @Nullable
  public Race getNewRace() {
    return newRace;
  }

  public void setNewRace(@Nullable Race newRace) {
    this.newRace = newRace;
  }

  @Override
  @NotNull
  public HandlerList getHandlers() {
    return HANDLERS;
  }
}
