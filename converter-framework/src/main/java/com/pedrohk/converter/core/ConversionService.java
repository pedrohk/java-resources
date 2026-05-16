package com.pedrohk.converter.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConversionService {
    private final Map<String, TypeConverter<?, ?>> converters = new ConcurrentHashMap<>();

    public <S, T> void register(TypeConverter<S, T> converter) {
        String key = createKey(converter.getSourceType(), converter.getTargetType());
        converters.put(key, converter);
    }

    @SuppressWarnings("unchecked")
    public <S, T> T convert(S source, Class<T> targetType) {
        if (source == null) return null;

        String key = createKey(source.getClass(), targetType);
        TypeConverter<S, T> converter = (TypeConverter<S, T>) converters.get(key);

        if (converter == null) {
            throw new IllegalArgumentException("No converter registered for " +
                    source.getClass().getSimpleName() + " to " + targetType.getSimpleName());
        }

        return converter.convert(source);
    }

    private String createKey(Class<?> source, Class<?> target) {
        return source.getName() + "->" + target.getName();
    }
}
