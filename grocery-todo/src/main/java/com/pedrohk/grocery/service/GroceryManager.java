package com.pedrohk.grocery.service;

import com.pedrohk.grocery.model.GroceryItem;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GroceryManager {
    private final Map<UUID, GroceryItem> items = new ConcurrentHashMap<>();

    public GroceryItem addItem(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be empty");
        var item = new GroceryItem(name);
        items.put(item.id(), item);
        return item;
    }

    public void removeItem(UUID id) {
        if (!items.containsKey(id)) throw new NoSuchElementException("Item not found");
        items.remove(id);
    }

    public void markAsDone(UUID id) {
        updateStatus(id, true);
    }

    public void redo(UUID id) {
        updateStatus(id, false);
    }

    private void updateStatus(UUID id, boolean completed) {
        var item = items.get(id);
        if (item == null) throw new NoSuchElementException("Item not found");
        items.put(id, item.withStatus(completed));
    }

    public List<GroceryItem> listAll() {
        return items.values().stream()
                .sorted(Comparator.comparing(GroceryItem::name))
                .toList();
    }

    public Optional<GroceryItem> findById(UUID id) {
        return Optional.ofNullable(items.get(id));
    }
}
