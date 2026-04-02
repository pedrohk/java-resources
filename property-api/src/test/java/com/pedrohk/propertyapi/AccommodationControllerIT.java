package com.pedrohk.propertyapi;

import com.pedrohk.propertyapi.model.Accommodation;
import com.pedrohk.propertyapi.repository.AccommodationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PropertyApiApplication.class)
class AccommodationControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockitoBean
    private AccommodationRepository repository;

    @Test
    void shouldCreateProperty() {
        var room = new Accommodation(UUID.randomUUID(), "Hotel Teste", "101", 200.0, LocalDate.now(), LocalDate.now());

        Mockito.when(repository.save(any())).thenReturn(room);

        var response = restTemplate.postForEntity("/properties", room, Accommodation.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().hotelName()).isEqualTo("Hotel Teste");
    }
}

