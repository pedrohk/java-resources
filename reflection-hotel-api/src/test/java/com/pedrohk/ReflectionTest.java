package com.pedrohk;

import com.pedrohk.model.HotelBooking;
import com.pedrohk.reflection.ReflectionEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ReflectionHotelApplication.class)
class ReflectionTest {

    private final ReflectionEngine engine = new ReflectionEngine();

    @Test
    void shouldManipulateBookingViaReflection() throws Exception {

        HotelBooking booking = new HotelBooking("Joao", 2, 100.0);


        engine.setPrivateField(booking, "guestName", "Pedro Henrique");


        String name = (String) engine.getPrivateField(booking, "guestName");
        assertThat(name).isEqualTo("Pedro Henrique");


        Double total = (Double) engine.callMethod(booking, "calculateTotal");
        assertThat(total).isEqualTo(200.0);
    }
}
