package com.pedrohk;

import com.pedrohk.patterns.creational.*;
import com.pedrohk.patterns.structural.*;
import com.pedrohk.patterns.behavioral.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class HotelGofSystemTest {

    @Test
    void testCreationalPatterns() {
        HotelInventory inventory1 = HotelInventory.getInstance();
        HotelInventory inventory2 = HotelInventory.getInstance();
        assertThat(inventory1).isSameAs(inventory2);

        Booking booking = new Booking.Builder()
                .guestName("Pedro")
                .withWifi()
                .build();
        assertThat(booking.getGuestName()).isEqualTo("Pedro");
    }

    @Test
    void testStructuralPatterns() {
        RoomService room = new MiniBarDecorator(new BaseRoom());
        assertThat(room.getCost()).isEqualTo(130.0);

        FrontDeskFacade facade = new FrontDeskFacade();
        facade.checkIn("Pedro");
    }

    @Test
    void testBehavioralPatterns() {
        PricingStrategy strategy = new HighSeasonStrategy();
        assertThat(strategy.applyPrice(100.0)).isEqualTo(180.0);

        RoomStatus status = new OccupiedStatus();
        status.showStatus();
    }
}
