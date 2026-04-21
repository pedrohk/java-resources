package com.pedrohk.queue;

import com.pedrohk.queue.model.Dish;
import com.pedrohk.queue.model.Order;
import com.pedrohk.queue.service.QueueService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QueueServiceTest {
    @Test
    void testWaitTimeEstimation() {
        QueueService service = new QueueService(2);
        Dish pizza = new Dish("Pizza", 20);
        Dish pasta = new Dish("Pasta", 10);

        service.placeOrder(new Order(1, pizza, LocalDateTime.now()));
        service.placeOrder(new Order(2, pizza, LocalDateTime.now()));

        int waitTime = service.estimateWaitTimeMinutes(pasta);
        assertEquals(30, waitTime);
    }

    @Test
    void testEmptyQueueEstimation() {
        QueueService service = new QueueService(1);
        Dish burger = new Dish("Burger", 15);
        assertEquals(15, service.estimateWaitTimeMinutes(burger));
    }

    @Test
    void testQueueCompletionReducesTime() {
        QueueService service = new QueueService(1);
        Dish dish = new Dish("Dish", 10);
        service.placeOrder(new Order(1, dish, LocalDateTime.now()));

        assertEquals(20, service.estimateWaitTimeMinutes(dish));
        service.completeOrder();
        assertEquals(10, service.estimateWaitTimeMinutes(dish));
    }

    @Test
    void testCapacityImpactOnEstimation() {
        QueueService highCapacity = new QueueService(10);
        QueueService lowCapacity = new QueueService(1);
        Dish longDish = new Dish("Steak", 50);

        highCapacity.placeOrder(new Order(1, longDish, LocalDateTime.now()));
        lowCapacity.placeOrder(new Order(2, longDish, LocalDateTime.now()));

        assertTrue(highCapacity.estimateWaitTimeMinutes(longDish) < lowCapacity.estimateWaitTimeMinutes(longDish));
    }
}
