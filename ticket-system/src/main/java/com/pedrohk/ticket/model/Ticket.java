package com.pedrohk.ticket.model;

import java.util.UUID;

public record Ticket(String ticketId, String showId, Seat seat) {
    public Ticket(String showId, Seat seat) {
        this(UUID.randomUUID().toString(), showId, seat);
    }
}
