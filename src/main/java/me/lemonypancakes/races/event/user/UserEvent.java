package me.lemonypancakes.races.event.user;

import me.lemonypancakes.races.user.User;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public abstract class UserEvent extends PlayerEvent {
  private final User user;

  public UserEvent(@NotNull User user) {
    super(user.getPlayer());
    this.user = user;
  }

  @NotNull
  public final User getUser() {
    return user;
  }
}
