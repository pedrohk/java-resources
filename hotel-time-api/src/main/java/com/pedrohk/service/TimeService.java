package com.pedrohk.service;

import com.pedrohk.model.BookingDuration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeService {

    public String formatArrival(LocalDateTime arrival) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return arrival.format(formatter);
    }

    public double calculateStayPrice(BookingDuration duration, double pricePerNight) {
        return duration.getTotalNights() * pricePerNight;
    }
}
