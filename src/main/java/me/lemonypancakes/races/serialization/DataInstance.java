package me.lemonypancakes.races.serialization;

import me.lemonypancakes.races.util.Unchecked;

import java.util.HashMap;
import java.util.Map;

public final class DataInstance {
    private final Map<String, Object> data;

    public DataInstance() {
        this.data = new HashMap<>();
    }

    public <T> T get(String key) {
        if (!data.containsKey(key)) throw new IllegalArgumentException("No value for key " + key);
        return Unchecked.cast(data.get(key));
    }

    public void set(String key, Object value) {
        this.data.put(key, value);
    }
}
