package me.lemonypancakes.races.serialization;

import java.util.function.Function;

public final class DataField<T> {
  private final DataType<T> dataType;
  private final T defaultValue;
  private final Function<DataContainer, T> defaultFunction;

  public DataField(final DataType<T> dataType) {
    this.dataType = dataType;
    this.defaultValue = null;
    this.defaultFunction = null;
  }

  public DataField(final DataType<T> dataType, final T defaultValue) {
    this.dataType = dataType;
    this.defaultValue = defaultValue;
    this.defaultFunction = null;
  }

  public DataField(final DataType<T> dataType, final Function<DataContainer, T> defaultFunction) {
    this.dataType = dataType;
    this.defaultValue = null;
    this.defaultFunction = defaultFunction;
  }

  public DataType<T> getDataType() {
    return this.dataType;
  }

  public boolean hasDefault() {
    return this.defaultValue != null || this.defaultFunction != null;
  }

  public T getDefault(final DataContainer container) {
    if (this.defaultValue != null) {
      return this.defaultValue;
    } else if (this.defaultFunction != null) {
      return this.defaultFunction.apply(container);
    } else {
      throw new IllegalStateException("No default value or function provided");
    }
  }
}
