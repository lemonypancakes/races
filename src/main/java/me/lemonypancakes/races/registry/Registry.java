package me.lemonypancakes.races.registry;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class Registry<K, V> {
  private final Map<K, V> registry = new ConcurrentHashMap<>();

  public synchronized V register(K key, V value) {
    Objects.requireNonNull(key, "key cannot be null");
    Objects.requireNonNull(value, "value cannot be null");
    return registry.putIfAbsent(key, value);
  }

  public synchronized V get(K key) {
    Objects.requireNonNull(key, "key cannot be null");
    return registry.get(key);
  }

  public synchronized boolean has(K key) {
    Objects.requireNonNull(key, "key cannot be null");
    return registry.containsKey(key);
  }
}
