package com.pedrohk;

import com.pedrohk.model.Booking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HotelRestTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateBookingWithConvertedCurrency() {
        Booking request = new Booking(null, "Pedro", 500.0, 0.0);

        ResponseEntity<Booking> response = restTemplate.postForEntity(
                "/api/hotel/bookings",
                request,
                Booking.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().guestName()).isEqualTo("Pedro");
        assertThat(response.getBody().priceInBRL()).isEqualTo(500.0);
        assertThat(response.getBody().priceInUSD()).isGreaterThan(0);
        assertThat(response.getBody().id()).isNotNull();
    }
}
