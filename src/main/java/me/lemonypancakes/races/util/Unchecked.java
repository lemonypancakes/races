package me.lemonypancakes.races.util;

@SuppressWarnings("unchecked")
public final class Unchecked {
  private Unchecked() {
    throw new UnsupportedOperationException("This class cannot be instantiated.");
  }

  public static <T> T cast(Object object) {
    return (T) object;
  }

  public static <T> Class<T> castClass(Class<?> clazz) {
    return (Class<T>) clazz;
  }
}
