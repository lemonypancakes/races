package me.lemonypancakes.races.util;

import org.bukkit.NamespacedKey;

public record TypedNamespacedKey<T>(Class<T> typeClass, NamespacedKey key) {
  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;

    TypedNamespacedKey<?> that = (TypedNamespacedKey<?>) o;
    return key.equals(that.key) && typeClass.equals(that.typeClass);
  }

  @Override
  public int hashCode() {
    int result = typeClass.hashCode();
    result = 31 * result + key.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "TypedNamespacedKey{" + "typeClass=" + typeClass + ", key=" + key + '}';
  }
}
