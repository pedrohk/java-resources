package com.pedrohk;

import com.pedrohk.service.FrontDeskService;
import com.pedrohk.service.InventoryService;
import com.pedrohk.service.MarketingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HotelInjectionTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private FrontDeskService frontDeskService;

    @Test
    @DisplayName("Should verify that Constructor Injection was used for InventoryService")
    void testConstructorInjectionRequirement() throws NoSuchFieldException {
        Field inventoryField = FrontDeskService.class.getDeclaredField("inventoryService");

        assertThat(Modifier.isFinal(inventoryField.getModifiers()))
                .as("Mandatory dependencies via constructor should be final")
                .isTrue();

        assertThat(frontDeskService.getInventoryService()).isNotNull();
    }

    @Test
    @DisplayName("Should verify that Setter Injection was used for MarketingService")
    void testSetterInjectionProvision() {
        assertThat(frontDeskService.getMarketingService()).isNotNull();
        assertThat(frontDeskService.getMarketingService()).isInstanceOf(MarketingService.class);
    }

    @Test
    @DisplayName("Should validate business logic with combined injections")
    void testBusinessLogic() {
        String result = frontDeskService.registerGuest(101);

        assertThat(result)
                .contains("Guest registered")
                .contains("Premium Hotel");
    }

    @Test
    @DisplayName("Should handle negative availability scenario")
    void testInvalidRoom() {
        String result = frontDeskService.registerGuest(999);
        assertThat(result).isEqualTo("No rooms available");
    }

    @Test
    @DisplayName("Should verify beans in the context")
    void testContextIntegrity() {
        assertThat(context.getBean(InventoryService.class)).isNotNull();
        assertThat(context.getBean(MarketingService.class)).isNotNull();
    }
}
