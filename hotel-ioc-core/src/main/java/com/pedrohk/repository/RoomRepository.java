package com.pedrohk.repository;

import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RoomRepository {
    private final Map<Integer, Boolean> rooms = new ConcurrentHashMap<>();

    public RoomRepository() {
        rooms.put(101, true);
        rooms.put(102, false);
        rooms.put(103, true);
    }

    public boolean isAvailable(int roomNumber) {
        return rooms.getOrDefault(roomNumber, false);
    }

    public void updateStatus(int roomNumber, boolean status) {
        rooms.put(roomNumber, status);
    }
}
