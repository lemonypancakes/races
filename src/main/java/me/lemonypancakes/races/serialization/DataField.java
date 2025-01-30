package me.lemonypancakes.races.serialization;

import java.util.function.Function;

public final class DataField<T> {
  private final DataType<T> type;
  private final T defaultValue;
  private final Function<DataContainer, T> defaultFunction;

  public DataField(DataType<T> type) {
    this.type = type;
    defaultValue = null;
    defaultFunction = null;
  }

  public DataField(DataType<T> type, T defaultValue) {
    this.type = type;
    this.defaultValue = defaultValue;
    defaultFunction = null;
  }

  public DataField(DataType<T> type, Function<DataContainer, T> defaultFunction) {
    this.type = type;
    defaultValue = null;
    this.defaultFunction = defaultFunction;
  }

  public DataType<T> getDataType() {
    return type;
  }

  public boolean hasDefault() {
    return defaultValue != null || defaultFunction != null;
  }

  public T getDefault(DataContainer container) {
    if (defaultValue != null) {
      return defaultValue;
    } else if (defaultFunction != null) {
      return defaultFunction.apply(container);
    } else {
      throw new IllegalStateException("No default value or function provided");
    }
  }
}
