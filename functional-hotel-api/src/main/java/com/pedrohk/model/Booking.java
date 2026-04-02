package com.pedrohk.model;

import java.util.UUID;

public record Booking(UUID id, String guestName, double price, boolean isPaid) {}
