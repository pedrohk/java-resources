package com.pedrohk.converter.core;

public interface TypeConverter<S, T> {
    T convert(S source);
    Class<S> getSourceType();
    Class<T> getTargetType();
}
