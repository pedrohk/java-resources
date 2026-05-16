package com.pedrohk.grocery;

import com.pedrohk.grocery.service.GroceryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GroceryManagerTest {
    private GroceryManager manager;

    @BeforeEach
    void setup() {
        manager = new GroceryManager();
    }

    @Test
    void shouldAddItemSuccessfully() {
        var item = manager.addItem("Milk");
        assertNotNull(item.id());
        assertEquals("Milk", item.name());
        assertFalse(item.completed());
    }

    @Test
    void shouldThrowExceptionOnEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> manager.addItem(""));
    }

    @Test
    void shouldRemoveItem() {
        var item = manager.addItem("Bread");
        manager.removeItem(item.id());
        assertTrue(manager.listAll().isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenRemovingNonExistent() {
        assertThrows(NoSuchElementException.class, () -> manager.removeItem(UUID.randomUUID()));
    }

    @Test
    void shouldMarkAsDone() {
        var item = manager.addItem("Eggs");
        manager.markAsDone(item.id());

        var updated = manager.findById(item.id()).orElseThrow();
        assertTrue(updated.completed());
    }

    @Test
    void shouldRedoItem() {
        var item = manager.addItem("Apples");
        manager.markAsDone(item.id());
        manager.redo(item.id());

        var updated = manager.findById(item.id()).orElseThrow();
        assertFalse(updated.completed());
    }

    @Test
    void shouldListAllItemsSortedByName() {
        manager.addItem("Zucchini");
        manager.addItem("Bananas");
        manager.addItem("Carrots");

        var list = manager.listAll();
        assertEquals(3, list.size());
        assertEquals("Bananas", list.get(0).name());
        assertEquals("Carrots", list.get(1).name());
        assertEquals("Zucchini", list.get(2).name());
    }

    @Test
    void testFullCycle() {
        var item = manager.addItem("Water");
        UUID id = item.id();

        manager.markAsDone(id);
        assertTrue(manager.findById(id).get().completed());

        manager.redo(id);
        assertFalse(manager.findById(id).get().completed());

        manager.removeItem(id);
        assertTrue(manager.findById(id).isEmpty());
    }
}
