package com.pedrohk;

import com.pedrohk.model.Booking;
import com.pedrohk.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = FunctionalHotelApplication.class)
class FunctionalBookingTest {

    private final BookingService service = new BookingService();

    @Test
    void shouldDemonstrateFunctionalInterfaces() {

        Booking b1 = service.createBooking(UUID::randomUUID, "Pedro", 500.0);
        Booking b2 = service.createBooking(UUID::randomUUID, "Henrique", 1200.0);
        List<Booking> bookings = List.of(b1, b2);

        List<Booking> expensiveOnes = service.filterBookings(bookings, b -> b.price() > 1000);
        assertThat(expensiveOnes).hasSize(1);

        List<String> names = service.transformBookings(bookings, b -> b.guestName().toUpperCase());
        assertThat(names).contains("PEDRO", "HENRIQUE");

        AtomicInteger count = new AtomicInteger();
        service.processBookings(bookings, b -> count.getAndIncrement());
        assertThat(count.get()).isEqualTo(2);
    }
}
