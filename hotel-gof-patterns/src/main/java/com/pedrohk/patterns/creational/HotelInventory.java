package com.pedrohk.patterns.creational;

public class HotelInventory {
    private static HotelInventory instance;
    private HotelInventory() {}
    public static synchronized HotelInventory getInstance() {
        if (instance == null) instance = new HotelInventory();
        return instance;
    }
}
