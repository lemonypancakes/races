package me.lemonypancakes.races;

public final class Bootstrap {
  private static boolean bootstrapped = false;

  private Bootstrap() {
    throw new UnsupportedOperationException();
  }

  public static void bootstrap() {
    if (bootstrapped) return;
    bootstrapped = true;
  }
}
