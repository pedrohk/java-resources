package com.pedrohk.ticket;

import com.pedrohk.ticket.model.Seat;
import com.pedrohk.ticket.model.Show;
import com.pedrohk.ticket.model.Ticket;
import com.pedrohk.ticket.model.Zone;
import com.pedrohk.ticket.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TicketServiceTest {
    private final String SHOW_ID = "ROCK_2025";
    private TicketService service;

    @BeforeEach
    void setup() {
        service = new TicketService();
        Show show = new Show(SHOW_ID, "Rock Fest",
                LocalDateTime.now().plusDays(10),
                Map.of(Zone.VIP, 2, Zone.GENERAL, 10));
        service.createShow(show);
    }

    @Test
    void shouldSellTicketsWhenSeatsAreAvailable() {
        List<Seat> seats = List.of(new Seat(1, Zone.VIP), new Seat(2, Zone.VIP));
        List<Ticket> tickets = service.purchaseTickets(SHOW_ID, seats);

        assertEquals(2, tickets.size());
        assertEquals(Zone.VIP, tickets.get(0).seat().zone());
    }

    @Test
    void shouldFailWhenZoneCapacityIsExceeded() {
        service.purchaseTickets(SHOW_ID, List.of(new Seat(1, Zone.VIP), new Seat(2, Zone.VIP)));

        List<Seat> extraSeat = List.of(new Seat(3, Zone.VIP));

        Exception ex = assertThrows(IllegalStateException.class, () ->
                service.purchaseTickets(SHOW_ID, extraSeat)
        );
        assertTrue(ex.getMessage().contains("is full"));
    }

    @Test
    void shouldFailWhenSpecificSeatIsAlreadySold() {
        Seat seat = new Seat(5, Zone.GENERAL);
        service.purchaseTickets(SHOW_ID, List.of(seat));

        assertThrows(IllegalStateException.class, () ->
                service.purchaseTickets(SHOW_ID, List.of(seat))
        );
    }

    @Test
    void shouldHandleMultipleShowsIndependently() {
        String otherShowId = "JAZZ_2025";
        service.createShow(new Show(otherShowId, "Jazz Night",
                LocalDateTime.now().plusDays(5), Map.of(Zone.VIP, 1)));

        assertDoesNotThrow(() -> {
            service.purchaseTickets(SHOW_ID, List.of(new Seat(1, Zone.VIP)));
            service.purchaseTickets(otherShowId, List.of(new Seat(1, Zone.VIP)));
        });
    }
}
