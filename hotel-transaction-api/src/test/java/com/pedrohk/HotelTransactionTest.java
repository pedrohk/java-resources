package com.pedrohk;

import com.pedrohk.model.Room;
import com.pedrohk.repository.RoomRepository;
import com.pedrohk.service.CheckOutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class HotelTransactionTest {

    @Autowired
    private CheckOutService checkOutService;

    @Autowired
    private RoomRepository roomRepository;

    @BeforeEach
    void setup() {
        roomRepository.deleteAll();
        roomRepository.save(new Room(101, "OCCUPIED"));
    }

    @Test
    void shouldCommitTransactionWhenNoFailure() {
        checkOutService.performCheckOut(101, false);

        Room room = roomRepository.findById(101).get();
        assertThat(room.getStatus()).isEqualTo("AVAILABLE");
    }

    @Test
    void shouldRollbackTransactionWhenFailureOccurs() {
        assertThatThrownBy(() -> checkOutService.performCheckOut(101, true))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Payment system failed");

        Room room = roomRepository.findById(101).get();
        assertThat(room.getStatus())
                .as("Room status should still be OCCUPIED due to rollback")
                .isEqualTo("OCCUPIED");
    }
}
