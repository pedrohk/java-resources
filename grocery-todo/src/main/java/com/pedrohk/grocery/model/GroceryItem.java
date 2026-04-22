package com.pedrohk.grocery.model;

import java.util.UUID;

public record GroceryItem(UUID id, String name, boolean completed) {
    public GroceryItem(String name) {
        this(UUID.randomUUID(), name, false);
    }

    public GroceryItem withStatus(boolean status) {
        return new GroceryItem(this.id, this.name, status);
    }
}
