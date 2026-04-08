package com.pedrohk;

import com.pedrohk.model.BookingDuration;
import com.pedrohk.service.TimeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    void shouldFormatDateTime() {
        LocalDateTime arrival = LocalDateTime.of(2026, 4, 10, 14, 30);
        String formatted = service.formatArrival(arrival);
        assertThat(formatted).isEqualTo("10/04/2026 14:30");
    }
}
