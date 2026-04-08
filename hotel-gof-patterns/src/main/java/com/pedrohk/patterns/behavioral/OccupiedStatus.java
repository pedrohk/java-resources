package com.pedrohk.patterns.behavioral;

public class OccupiedStatus implements RoomStatus {
    @Override
    public void showStatus() {
        System.out.println("Room is currently occupied.");
    }
}
