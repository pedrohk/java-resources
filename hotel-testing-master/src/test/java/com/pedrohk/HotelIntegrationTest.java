package com.pedrohk;

import com.pedrohk.model.Booking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class HotelIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private Environment environment;

    @Test
    @DisplayName("Should verify Environment properties and Active Profiles")
    void testEnvironmentConfiguration() {
        assertThat(environment.getActiveProfiles()).contains("test");
        assertThat(environment.getProperty("hotel.test.custom-value")).isEqualTo("IntegrationTestValue");
        assertThat(environment.getProperty("local.server.port")).isNotNull();
    }

    @Test
    @DisplayName("Should successfully create a booking through WebTestClient")
    void shouldCreateBookingSuccessfully() {
        Booking request = new Booking(null, "Pedro Henrique", "Presidential Suite", 1500.0);

        webTestClient.post()
                .uri("/api/v1/bookings")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Booking.class)
                .consumeWith(response -> {
                    Booking body = response.getResponseBody();
                    assertThat(body).isNotNull();
                    assertThat(body.id()).isNotNull();
                    assertThat(body.guestName()).isEqualTo("Pedro Henrique");
                    assertThat(body.price()).isEqualTo(1500.0);
                });
    }

    @Test
    @DisplayName("Should return 400 Bad Request when guest name is missing")
    void shouldReturnBadRequestForInvalidBooking() {
        Booking invalidRequest = new Booking(null, "", "Standard", 100.0);

        webTestClient.post()
                .uri("/api/v1/bookings")
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("Should verify API Health Check endpoint")
    void shouldVerifyHealthCheck() {
        webTestClient.get()
                .uri("/api/v1/bookings/health")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("OK");
    }
}
