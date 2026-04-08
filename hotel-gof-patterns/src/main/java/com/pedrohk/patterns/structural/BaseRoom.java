package com.pedrohk.patterns.structural;

public class BaseRoom implements RoomService {
    @Override
    public double getCost() {
        return 100.0;
    }
}
