package com.pedrohk.logistic;

import com.pedrohk.logistic.model.Dimensions;
import com.pedrohk.logistic.model.TransportType;
import com.pedrohk.logistic.service.LogisticService;
import com.pedrohk.logistic.strategy.DynamicPricingRegistry;
import com.pedrohk.logistic.strategy.impl.BoatStrategy;
import com.pedrohk.logistic.strategy.impl.TruckStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LogisticSystemTest {
    private DynamicPricingRegistry registry;
    private LogisticService service;

    @BeforeEach
    void setup() {
        registry = new DynamicPricingRegistry();
        service = new LogisticService(registry);
    }

    @Test
    void testDynamicPriceUpdate() {
        Dimensions dim = new Dimensions(10, 10, 10);

        registry.updateStrategy(TransportType.TRUCK, new TruckStrategy(1.0));
        double firstPrice = service.calculateFreight(TransportType.TRUCK, dim, 100);

        registry.updateStrategy(TransportType.TRUCK, new TruckStrategy(2.0));
        double secondPrice = service.calculateFreight(TransportType.TRUCK, dim, 100);

        assertNotEquals(firstPrice, secondPrice);
        assertEquals(2050.0, secondPrice);
    }

    @Test
    void testBoatCalculation() {
        registry.updateStrategy(TransportType.BOAT, new BoatStrategy());
        Dimensions dim = new Dimensions(2, 2, 2);
        double price = service.calculateFreight(TransportType.BOAT, dim, 50);
        assertEquals(6.6, price, 0.001);
    }

    @Test
    void testMissingStrategyThrowsException() {
        Dimensions dim = new Dimensions(1, 1, 1);
        assertThrows(IllegalArgumentException.class, () ->
                service.calculateFreight(TransportType.RAIL, dim, 10)
        );
    }

    @Test
    void testVolumeCalculation() {
        Dimensions dim = new Dimensions(5, 4, 3);
        assertEquals(60.0, dim.volume());
    }
}
