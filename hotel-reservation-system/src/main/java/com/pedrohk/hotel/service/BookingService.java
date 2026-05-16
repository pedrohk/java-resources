package com.pedrohk.hotel.service;

import com.pedrohk.hotel.auth.RequiresAuth;
import com.pedrohk.hotel.model.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BookingService {
    private final Map<Integer, List<Reservation>> roomBookings = new ConcurrentHashMap<>();

    @RequiresAuth(role = "USER")
    public Reservation bookRoom(int roomNumber, LocalDate start, LocalDate end, String email) {

        if (start == null || end == null || !start.isBefore(end)) {
            throw new IllegalArgumentException("Invalid date range");
        }

        validateAvailability(roomNumber, start, end);

        Reservation res = new Reservation(UUID.randomUUID(), email, roomNumber, start, end);
        roomBookings.computeIfAbsent(roomNumber, k -> new ArrayList<>()).add(res);
        return res;
    }

    private void validateAvailability(int roomNumber, LocalDate start, LocalDate end) {
        List<Reservation> existing = roomBookings.getOrDefault(roomNumber, List.of());
        boolean overlap = existing.stream().anyMatch(r ->
                (start.isBefore(r.checkOut()) && end.isAfter(r.checkIn()))
        );
        if (overlap) throw new IllegalStateException("Room already booked");
    }
}
