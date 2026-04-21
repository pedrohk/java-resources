package com.pedrohk.factory;

import com.pedrohk.factory.inventory.InventoryManager;
import com.pedrohk.factory.model.Guitar;
import com.pedrohk.factory.model.GuitarModel;
import com.pedrohk.factory.model.Specs;

import java.util.UUID;

public class GuitarFactory {
    private final InventoryManager inventory;

    public GuitarFactory(InventoryManager inventory) {
        this.inventory = inventory;
    }

    public Guitar buildGuitar(GuitarModel model, Specs specs) {
        validateInventory(specs);

        consumeComponents(specs);

        return new Guitar(
                UUID.randomUUID().toString(),
                model,
                specs
        );
    }

    private void validateInventory(Specs specs) {
        if (inventory.getStockLevel(specs.wood()) <= 0)
            throw new IllegalStateException("Missing wood: " + specs.wood());
        if (inventory.getStockLevel(specs.pickups()) <= 0)
            throw new IllegalStateException("Missing pickups: " + specs.pickups());
    }

    private void consumeComponents(Specs specs) {
        inventory.consume(specs.wood());
        inventory.consume(specs.pickups());
    }
}
