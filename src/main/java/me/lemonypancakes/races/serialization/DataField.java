package me.lemonypancakes.races.serialization;

import java.util.function.Function;

public final class DataField<T> {
  private final DataType<T> dataType;
  private final T defaultValue;
  private final Function<DataInstance, T> defaultFunction;

  public DataField(DataType<T> dataType) {
    this.dataType = dataType;
    this.defaultValue = null;
    this.defaultFunction = null;
  }

  public DataField(DataType<T> dataType, T defaultValue) {
    this.dataType = dataType;
    this.defaultValue = defaultValue;
    this.defaultFunction = null;
  }

  public DataField(DataType<T> dataType, Function<DataInstance, T> defaultFunction) {
    this.dataType = dataType;
    this.defaultValue = null;
    this.defaultFunction = defaultFunction;
  }

  public DataType<T> getDataType() {
    return dataType;
  }

  public boolean hasDefault() {
    return defaultValue != null || defaultFunction != null;
  }

  public T getDefault(DataInstance instance) {
    if (defaultValue != null) {
      return defaultValue;
    } else if (defaultFunction != null) {
      return defaultFunction.apply(instance);
    } else {
      throw new IllegalStateException("No default value or function provided");
    }
  }
}
