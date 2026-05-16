package com.pedrohk.queue;

import com.pedrohk.queue.model.Dish;
import com.pedrohk.queue.model.Order;
import com.pedrohk.queue.service.QueueService;
import com.pedrohk.router.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueueServiceTest {
    private QueueService service;
    private NotificationService notificationService;

    @BeforeEach
    void setup() {
        notificationService = new NotificationService();
        service = new QueueService(1, notificationService);
    }

    @Test
    void testWaitTimeEstimation() {
        Dish pizza = new Dish("Pizza", 20);
        service.placeOrder(new Order(1, "c1@test.com", pizza, LocalDateTime.now()));


        assertEquals(40, service.estimateWaitTimeMinutes(pizza));
    }

    @Test
    void testEmptyQueueEstimation() {
        Dish burger = new Dish("Burger", 15);
        assertEquals(15, service.estimateWaitTimeMinutes(burger));
    }

    @Test
    void testQueueCompletionReducesTime() {
        Dish dish = new Dish("Dish", 10);
        service.placeOrder(new Order(1, "c1@test.com", dish, LocalDateTime.now()));

        assertEquals(20, service.estimateWaitTimeMinutes(dish));

        service.completeOrder();

        assertEquals(10, service.estimateWaitTimeMinutes(dish));
    }

    @Test
    void testCapacityImpact() {
        QueueService multiChef = new QueueService(2, notificationService);
        Dish pasta = new Dish("Pasta", 20);

        assertEquals(10, multiChef.estimateWaitTimeMinutes(pasta));
    }
}
