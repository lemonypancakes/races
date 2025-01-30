package me.lemonypancakes.races.util;

@SuppressWarnings("unchecked")
public final class Unchecked {
  private Unchecked() {
    throw new UnsupportedOperationException();
  }

  public static <T> T cast(final Object object) {
    return (T) object;
  }

  public static <T> Class<T> castClass(final Class<?> clazz) {
    return (Class<T>) clazz;
  }
}
