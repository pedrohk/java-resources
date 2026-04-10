package com.pedrohk;

import com.pedrohk.processor.HotelQualityProcessor;
import com.pedrohk.service.CleaningService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HotelBppDeepTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private CleaningService cleaningService;

    @Autowired
    private HotelQualityProcessor qualityProcessor;

    @Test
    @DisplayName("Should verify BPP modified the bean state during initialization")
    void testBppStateModification() {
        assertThat(cleaningService.isInspected())
                .as("The BPP should have set inspected to true during post-initialization")
                .isTrue();
    }

    @Test
    @DisplayName("Should verify the order of BPP execution phases")
    void testBppExecutionOrder() {
        assertThat(qualityProcessor.getProcessedBeans())
                .containsExactly("BEFORE:cleaningService", "AFTER:cleaningService");
    }

    @Test
    @DisplayName("Should verify that BPP only processed beans with @AuditLog")
    void testBppSelectiveProcessing() {
        assertThat(qualityProcessor.getProcessedBeans())
                .noneMatch(name -> name.contains("hotelBppApplication"));
    }

    @Test
    @DisplayName("Should verify bean functionality remains intact after BPP processing")
    void testBeanFunctionalIntegrity() {
        assertThat(cleaningService.performCleaning()).isEqualTo("Room is clean");
    }
}
