package com.pedrohk.factory;

import com.pedrohk.factory.inventory.InventoryManager;
import com.pedrohk.factory.model.Guitar;
import com.pedrohk.factory.model.GuitarModel;
import com.pedrohk.factory.model.Specs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GuitarFactoryTest {
    private InventoryManager inventory;
    private GuitarFactory factory;

    @BeforeEach
    void setup() {
        inventory = new InventoryManager();
        factory = new GuitarFactory(inventory);
    }

    @Test
    void shouldBuildGuitarWhenInventoryIsAvailable() {
        inventory.addStock("Mahogany", 1);
        inventory.addStock("Humbucker", 1);

        Specs specs = new Specs("Mahogany", "Humbucker", "Gold Top", 6);
        Guitar guitar = factory.buildGuitar(GuitarModel.LES_PAUL, specs);

        assertNotNull(guitar.serialNumber());
        assertEquals(GuitarModel.LES_PAUL, guitar.model());
        assertEquals(0, inventory.getStockLevel("Mahogany"));
    }

    @Test
    void shouldFailWhenMissingWood() {
        inventory.addStock("Humbucker", 1);
        Specs specs = new Specs("Alder", "Humbucker", "Sunburst", 6);

        Exception exception = assertThrows(IllegalStateException.class, () ->
                factory.buildGuitar(GuitarModel.STRATOCASTER, specs)
        );
        assertTrue(exception.getMessage().contains("Missing wood"));
    }

    @Test
    void testInventoryDecrement() {
        inventory.addStock("Maple", 10);
        inventory.addStock("SingleCoil", 10);
        Specs specs = new Specs("Maple", "SingleCoil", "White", 6);

        factory.buildGuitar(GuitarModel.TELECASTER, specs);

        assertEquals(9, inventory.getStockLevel("Maple"));
        assertEquals(9, inventory.getStockLevel("SingleCoil"));
    }

    @Test
    void integrationBuildMultipleGuitarsUntilStockOut() {
        inventory.addStock("Ash", 2);
        inventory.addStock("Vintage", 5);
        Specs specs = new Specs("Ash", "Vintage", "Natural", 6);

        assertNotNull(factory.buildGuitar(GuitarModel.STRATOCASTER, specs));
        assertNotNull(factory.buildGuitar(GuitarModel.STRATOCASTER, specs));

        assertThrows(IllegalStateException.class, () ->
                factory.buildGuitar(GuitarModel.STRATOCASTER, specs)
        );
    }
}
