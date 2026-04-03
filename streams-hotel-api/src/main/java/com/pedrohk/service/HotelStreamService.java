package com.pedrohk.service;

import com.pedrohk.model.Room;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HotelStreamService {

    public List<Room> filterAvailableRooms(List<Room> rooms) {
        return rooms.stream()
                .filter(room -> !room.isOccupied())
                .toList();
    }

    public List<String> getRoomNumbersByType(List<Room> rooms, String type) {
        return rooms.stream()
                .filter(room -> room.type().equalsIgnoreCase(type))
                .map(Room::number)
                .toList();
    }

    public double calculateTotalValue(Room[] roomsArray) {
        return Arrays.stream(roomsArray)
                .mapToDouble(Room::price)
                .sum();
    }

    public Map<String, List<Room>> groupRoomsByType(List<Room> rooms) {
        return rooms.stream()
                .collect(Collectors.groupingBy(Room::type));
    }
}
