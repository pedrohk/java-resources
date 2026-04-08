package com.pedrohk.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record BookingDuration(LocalDate checkIn, LocalDate checkOut) {
    public long getTotalNights() {
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }
}
