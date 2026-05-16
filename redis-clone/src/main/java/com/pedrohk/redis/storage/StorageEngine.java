package com.pedrohk.redis.storage;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class StorageEngine {
    private final Map<String, String> strings = new ConcurrentHashMap<>();
    private final Map<String, Map<String, String>> maps = new ConcurrentHashMap<>();

    public void set(String key, String value) {
        strings.put(key, value);
    }

    public String get(String key) {
        return strings.get(key);
    }

    public void remove(String key) {
        strings.remove(key);
    }

    public void append(String key, String value) {
        strings.merge(key, value, (oldVal, newVal) -> oldVal + newVal);
    }

    public void hset(String key, String field, String value) {
        maps.computeIfAbsent(key, k -> new ConcurrentHashMap<>()).put(field, value);
    }

    public String hget(String key, String field) {
        Map<String, String> map = maps.get(key);
        return (map != null) ? map.get(field) : null;
    }

    public Set<String> hkeys(String key) {
        Map<String, String> map = maps.get(key);
        return (map != null) ? map.keySet() : Collections.emptySet();
    }

    public Collection<String> hvals(String key) {
        Map<String, String> map = maps.get(key);
        return (map != null) ? map.values() : Collections.emptyList();
    }
}
