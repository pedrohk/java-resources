package com.pedrohk.hotel.model;

import java.time.LocalDate;
import java.util.UUID;

public record Reservation(UUID id, String userEmail, int roomNumber, LocalDate checkIn, LocalDate checkOut) {}
