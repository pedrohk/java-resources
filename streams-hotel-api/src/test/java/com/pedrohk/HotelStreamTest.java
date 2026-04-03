package com.pedrohk;

import com.pedrohk.model.Room;
import com.pedrohk.service.HotelStreamService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = StreamsHotelApplication.class)
class HotelStreamTest {

    private final HotelStreamService service = new HotelStreamService();

    @Test
    void shouldProcessHotelDataWithStreams() {
        List<Room> rooms = List.of(
                new Room("101", "Deluxe", 500.0, false),
                new Room("102", "Standard", 200.0, true),
                new Room("201", "Deluxe", 500.0, true),
                new Room("202", "Suite", 1000.0, false)
        );

        List<Room> available = service.filterAvailableRooms(rooms);
        assertThat(available).hasSize(2).extracting(Room::number).containsExactly("101", "202");

        List<String> deluxeNumbers = service.getRoomNumbersByType(rooms, "Deluxe");
        assertThat(deluxeNumbers).containsExactly("101", "201");

        Room[] roomsArray = rooms.toArray(new Room[0]);
        double total = service.calculateTotalValue(roomsArray);
        assertThat(total).isEqualTo(2200.0);

        Map<String, List<Room>> grouped = service.groupRoomsByType(rooms);
        assertThat(grouped.get("Standard")).hasSize(1);
        assertThat(grouped.get("Deluxe")).hasSize(2);
    }
}
