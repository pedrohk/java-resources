package com.pedrohk;

import com.pedrohk.model.BookingDuration;
import com.pedrohk.service.TimeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = HotelTimeApplication.class)
class TimeApiTest {

    private final TimeService service = new TimeService();

    @Test
    void shouldCalculateStayCorrectly() {
        LocalDate checkIn = LocalDate.of(2026, 4, 10);
        LocalDate checkOut = LocalDate.of(2026, 4, 15);
        BookingDuration duration = new BookingDuration(checkIn, checkOut);

        double totalPrice = service.calculateStayPrice(duration, 200.0);

        assertThat(duration.getTotalNights()).isEqualTo(5);
        assertThat(totalPrice).isEqualTo(1000.0);
    }

    @Test
    void shouldHandleTimeZonesForInternationalGuests() {
        ZonedDateTime tokyoTime = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
        ZonedDateTime saoPauloTime = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));

        assertThat(tokyoTime.getHour()).isNotEqualTo(saoPauloTime.getHour());
    }

    @Test
    void shouldIdentifyLateCheckIn() {
        LocalTime checkInLimit = LocalTime.of(14, 0);
        LocalTime guestArrivalTime = LocalTime.of(16, 30);

        assertThat(guestArrivalTime).isAfter(checkInLimit);
    }

    @Test
    void shouldCalculateDaysUntilNewYearSale() {
        LocalDate today = LocalDate.of(2026, 4, 8);
        LocalDate nextYear = LocalDate.of(2027, 1, 1);

        long daysUntilSale = ChronoUnit.DAYS.between(today, nextYear);

        assertThat(daysUntilSale).isGreaterThan(0);
    }

    @Test
    void shouldCheckIfGuestIsLeapYearBorn() {
        LocalDate birthday = LocalDate.of(2000, 2, 29);
        assertThat(birthday.isLeapYear()).isTrue();
    }

    @Test
    void shouldFormatDateTime() {
        LocalDateTime arrival = LocalDateTime.of(2026, 4, 10, 14, 30);
        String formatted = service.formatArrival(arrival);
        assertThat(formatted).isEqualTo("10/04/2026 14:30");
    }
}
