package com.pedrohk.hotel;

import com.pedrohk.hotel.auth.SecurityContext;
import com.pedrohk.hotel.auth.SecurityInterceptor;
import com.pedrohk.hotel.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HotelSystemTest {
    private BookingService bookingService;
    private SecurityInterceptor interceptor;

    @BeforeEach
    void setup() {
        bookingService = new BookingService();
        interceptor = new SecurityInterceptor();
        SecurityContext.clear();
    }

    @Test
    void shouldAllowBookingWithUserRole() throws Exception {
        SecurityContext.setRole("USER");
        interceptor.inspect(bookingService, "bookRoom");

        var res = bookingService.bookRoom(101, LocalDate.now(), LocalDate.now().plusDays(1), "test@test.com");
        assertNotNull(res);
    }

    @Test
    void shouldDenyBookingWithoutRole() {
        assertThrows(SecurityException.class, () -> interceptor.inspect(bookingService, "bookRoom"));
    }

    @Test
    void adminShouldHaveFullAccess() throws Exception {
        SecurityContext.setRole("ADMIN");
        assertDoesNotThrow(() -> interceptor.inspect(bookingService, "bookRoom"));
    }

    @Test
    void shouldBlockOverlappingReservations() {
        SecurityContext.setRole("USER");
        LocalDate checkIn = LocalDate.of(2025, 12, 1);
        LocalDate checkOut = LocalDate.of(2025, 12, 10);

        bookingService.bookRoom(202, checkIn, checkOut, "user1@test.com");

        assertThrows(IllegalStateException.class, () ->
                bookingService.bookRoom(202, checkIn.plusDays(5), checkOut.plusDays(5), "user2@test.com")
        );
    }

    @Test
    void shouldAllowConsecutiveBookings() {
        SecurityContext.setRole("USER");
        LocalDate midDate = LocalDate.now().plusDays(2);

        bookingService.bookRoom(303, LocalDate.now(), midDate, "user1@test.com");
        assertNotNull(bookingService.bookRoom(303, midDate, midDate.plusDays(2), "user2@test.com"));
    }

    @Test
    void shouldFailOnInvalidDateRange() {
        SecurityContext.setRole("USER");

        LocalDate start = LocalDate.now().plusDays(5);
        LocalDate end = LocalDate.now();

        assertThrows(IllegalArgumentException.class, () ->
                bookingService.bookRoom(404, start, end, "error@test.com")
        );
    }

}
