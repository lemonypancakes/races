package me.lemonypancakes.races.serialization;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Data {
    private final Map<String, DataField<?>> dataFields;

    public Data() {
        this.dataFields = new HashMap<>();
    }

    public <T> Data add(String key, DataField<T> field) {
        dataFields.put(key, field);
        return this;
    }

    public <T> Data add(String key, DataType<T> dataType) {
        add(key, new DataField<>(dataType));
        return this;
    }

    public <T> Data add(String key, DataType<T> dataType, T defaultValue) {
        add(key, new DataField<>(dataType, defaultValue));
        return this;
    }

    public <T> Data add(String key, DataType<T> dataType, Function<DataInstance, T> defaultFunction) {
        add(key, new DataField<>(dataType, defaultFunction));
        return this;
    }

    public DataField<?> getField(String key) {
        if (!dataFields.containsKey(key)) throw new IllegalArgumentException("No field for key " + key);
        return dataFields.get(key);
    }

    public DataInstance read(JsonObject data) {
        DataInstance instance = new DataInstance();

        dataFields.forEach((key, field) -> {
            if (data.has(key)) {
                instance.set(key, field.getDataType().read(data.get(key)));
            } else if (field.hasDefault()) {
                instance.set(key, field.getDefault(instance));
            } else {
                throw new IllegalArgumentException("No value for key " + key);
            }
        });
        return instance;
    }
}
