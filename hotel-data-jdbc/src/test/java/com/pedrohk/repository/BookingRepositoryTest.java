package com.pedrohk.repository;

import com.pedrohk.model.Booking;
import com.pedrohk.model.ServiceItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookingRepositoryTest {

    @Autowired
    private BookingRepository repository;

    @Test
    void shouldSaveAndLoadBookingWithItems() {
        ServiceItem item1 = new ServiceItem(null, "Breakfast", 30.0);
        ServiceItem item2 = new ServiceItem(null, "Soda", 5.0);

        Booking booking = new Booking(null, "Pedro", "101", Set.of(item1, item2));

        Booking saved = repository.save(booking);

        assertThat(saved.id()).isNotNull();
        assertThat(saved.items()).hasSize(2);

        Booking fetched = repository.findById(saved.id()).orElseThrow();
        assertThat(fetched.guestName()).isEqualTo("Pedro");
        assertThat(fetched.items()).extracting("description").containsExactlyInAnyOrder("Breakfast", "Soda");
    }
}
