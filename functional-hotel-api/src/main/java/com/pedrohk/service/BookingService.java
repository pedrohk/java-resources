package com.pedrohk.service;

import com.pedrohk.model.Booking;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BookingService {

    public Booking createBooking(Supplier<UUID> idGenerator, String name, double price) {
        return new Booking(idGenerator.get(), name, price, false);
    }

    public List<Booking> filterBookings(List<Booking> list, Predicate<Booking> condition) {
        return list.stream().filter(condition).toList();
    }

    public List<String> transformBookings(List<Booking> list, Function<Booking, String> mapper) {
        return list.stream().map(mapper).toList();
    }

    public void processBookings(List<Booking> list, Consumer<Booking> action) {
        list.forEach(action);
    }
}
