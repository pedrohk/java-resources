package com.pedrohk;

import com.pedrohk.model.Room;
import com.pedrohk.service.HotelOperatorService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = FunctionalAdvancedApplication.class)
class HotelOperatorTest {

    private final HotelOperatorService service = new HotelOperatorService();

    @Test
    void shouldExecuteAdvancedFunctionalOperations() {

        Room r1 = service.createRoom(UUID::randomUUID, "Suite", 1000.0);
        Room r2 = service.createRoom(UUID::randomUUID, "Standard", 500.0);
        List<Room> rooms = List.of(r1, r2);


        List<Room> taxedRooms = service.applyPriceAdjustment(rooms, p -> p * 1.10);
        assertThat(taxedRooms.get(0).price()).isEqualTo(1100.0);
        assertThat(taxedRooms.get(1).price()).isEqualTo(550.0);


        double total = service.calculateTotalRevenue(taxedRooms, Double::sum);
        assertThat(total).isEqualTo(1650.0);


        List<String> auditLog = new ArrayList<>();
        service.auditRooms(rooms, r -> auditLog.add("Auditing room: " + r.type()));
        assertThat(auditLog).hasSize(2).contains("Auditing room: Suite");
    }
}
