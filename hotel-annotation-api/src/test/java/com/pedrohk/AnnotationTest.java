package com.pedrohk;

import com.pedrohk.processor.SecurityProcessor;
import com.pedrohk.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = HotelAnnotationApplication.class)
class AnnotationTest {

    private final BookingService service = new BookingService();

    @Test
    void shouldBlockAccessWhenNotVip() {
        assertThatThrownBy(() -> SecurityProcessor.process(service, "bookPresidentialSuite", false))
                .isInstanceOf(SecurityException.class)
                .hasMessage("Attention: Only VIP guests can book the Presidential Suite!");
    }

    @Test
    void shouldAllowAccessWhenVip() throws Exception {
        Object result = SecurityProcessor.process(service, "bookPresidentialSuite", true);
        assertThat(result).isEqualTo("Presidential Suite booked");
    }

    @Test
    void shouldAllowStandardRoomForAnyone() throws Exception {
        Object result = SecurityProcessor.process(service, "bookStandardRoom", false);
        assertThat(result).isEqualTo("Standard room booked");
    }
}
