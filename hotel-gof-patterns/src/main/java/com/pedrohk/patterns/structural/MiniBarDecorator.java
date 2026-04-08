package com.pedrohk.patterns.structural;

public class MiniBarDecorator implements RoomService {
    private final RoomService roomService;

    public MiniBarDecorator(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public double getCost() {
        return roomService.getCost() + 30.0;
    }
}
