package me.lemonypancakes.races.registry;

import java.util.Map;

public final class Registry<K, V> {
  private final Map<K, V> registry;

  public Registry(Map<K, V> registry) {
    this.registry = registry;
  }

  public synchronized V register(K key, V value) {
    return registry.putIfAbsent(key, value);
  }

  public synchronized V get(K key) {
    return registry.get(key);
  }

  public synchronized boolean has(K key) {
    return registry.containsKey(key);
  }
}
