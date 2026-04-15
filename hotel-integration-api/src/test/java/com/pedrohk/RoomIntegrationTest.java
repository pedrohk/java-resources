package com.pedrohk;

import com.pedrohk.model.Room;
import com.pedrohk.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoomIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RoomRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void shouldCreateAndRetrieveRoom() {
        Room room = new Room("101", "Deluxe", false);

        ResponseEntity<Room> postResponse = restTemplate.postForEntity("/api/rooms", room, Room.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postResponse.getBody().getId()).isNotNull();

        ResponseEntity<Room[]> getResponse = restTemplate.getForEntity("/api/rooms", Room[].class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).hasSize(1);
        assertThat(getResponse.getBody()[0].getNumber()).isEqualTo("101");
    }

    @Test
    void shouldUpdateRoomStatusToOccupied() {
        Room savedRoom = repository.save(new Room("202", "Standard", false));

        ResponseEntity<Room> patchResponse = restTemplate.exchange(
                "/api/rooms/" + savedRoom.getId() + "/occupy",
                HttpMethod.PATCH,
                HttpEntity.EMPTY,
                Room.class
        );

        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(patchResponse.getBody().isOccupied()).isTrue();

        Room updatedInDb = repository.findById(savedRoom.getId()).orElseThrow();
        assertThat(updatedInDb.isOccupied()).isTrue();
    }
}
