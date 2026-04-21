package com.pedrohk.factory.inventory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryManager {
    private final Map<String, Integer> stock = new ConcurrentHashMap<>();

    public void addStock(String item, int quantity) {
        stock.merge(item, quantity, Integer::sum);
    }

    public boolean consume(String item) {
        return stock.computeIfPresent(item, (k, v) -> v > 0 ? v - 1 : null) != null;
    }

    public int getStockLevel(String item) {
        return stock.getOrDefault(item, 0);
    }
}
