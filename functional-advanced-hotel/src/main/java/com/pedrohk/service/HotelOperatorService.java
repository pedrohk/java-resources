package com.pedrohk.service;

import com.pedrohk.model.Room;
import java.util.List;
import java.util.UUID;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class HotelOperatorService {

    public Room createRoom(Supplier<UUID> idGenerator, String type, double price) {
        return new Room(idGenerator.get(), type, price);
    }

    public List<Room> applyPriceAdjustment(List<Room> rooms, UnaryOperator<Double> adjustment) {
        return rooms.stream()
                .map(r -> new Room(r.id(), r.type(), adjustment.apply(r.price())))
                .toList();
    }

    public double calculateTotalRevenue(List<Room> rooms, BinaryOperator<Double> aggregator) {
        return rooms.stream()
                .map(Room::price)
                .reduce(0.0, aggregator);
    }

    public void auditRooms(List<Room> rooms, Consumer<Room> auditor) {
        rooms.forEach(auditor);
    }
}
