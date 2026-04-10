package com.pedrohk;

import com.pedrohk.service.HotelConfigService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HotelValueTest {

    @Autowired
    private HotelConfigService configService;

    @Test
    @DisplayName("Should inject simple value from properties file")
    void testSimpleInjection() {
        assertThat(configService.getHotelName()).isEqualTo("Pedro Palace Hotel");
        assertThat(configService.getBasePrice()).isEqualTo(250.0);
    }

    @Test
    @DisplayName("Should use default value when property is missing")
    void testDefaultValueInjection() {
        assertThat(configService.getDiscountRate()).isEqualTo(0.05);
    }

    @Test
    @DisplayName("Should calculate complex expression using SPEL")
    void testSpelCalculation() {
        double expected = 250.0 * 1.15;
        assertThat(configService.getFinalPriceWithTax()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should inject and parse list of integers")
    void testListInjection() {
        assertThat(configService.getAvailableFloors())
                .hasSize(5)
                .containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    @DisplayName("Should inject system properties from the environment")
    void testSystemPropertyInjection() {
        assertThat(configService.getUserHomeFolder()).isNotEmpty();
        assertThat(configService.getUserHomeFolder()).containsAnyOf("/", "\\");
    }
}
