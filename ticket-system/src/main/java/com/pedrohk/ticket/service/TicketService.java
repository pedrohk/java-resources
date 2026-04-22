package com.pedrohk.ticket.service;

import com.pedrohk.ticket.model.Seat;
import com.pedrohk.ticket.model.Show;
import com.pedrohk.ticket.model.Ticket;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class TicketService {
    private final Map<String, List<Ticket>> soldTickets = new ConcurrentHashMap<>();
    private final Map<String, Show> shows = new ConcurrentHashMap<>();

    public void createShow(Show show) {
        shows.put(show.id(), show);
        soldTickets.put(show.id(), new CopyOnWriteArrayList<>());
    }

    public List<Ticket> purchaseTickets(String showId, List<Seat> requestedSeats) {
        Show show = Optional.ofNullable(shows.get(showId))
                .orElseThrow(() -> new IllegalArgumentException("Show not found"));

        synchronized (soldTickets.get(showId)) {
            validateAvailability(show, requestedSeats);

            List<Ticket> newTickets = requestedSeats.stream()
                    .map(seat -> new Ticket(showId, seat))
                    .toList();

            soldTickets.get(showId).addAll(newTickets);
            return newTickets;
        }
    }

    private void validateAvailability(Show show, List<Seat> requestedSeats) {
        List<Ticket> currentTickets = soldTickets.get(show.id());

        for (Seat seat : requestedSeats) {
            boolean alreadyTaken = currentTickets.stream()
                    .anyMatch(t -> t.seat().equals(seat));

            if (alreadyTaken) {
                throw new IllegalStateException("Seat " + seat.number() + " already sold");
            }

            long zoneCount = currentTickets.stream()
                    .filter(t -> t.seat().zone() == seat.zone())
                    .count();

            int maxZoneCapacity = show.capacity().getOrDefault(seat.zone(), 0);
            if (zoneCount >= maxZoneCapacity) {
                throw new IllegalStateException("Zone " + seat.zone() + " is full");
            }
        }
    }
}
