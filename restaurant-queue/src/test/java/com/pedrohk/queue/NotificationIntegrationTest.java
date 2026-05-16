package com.pedrohk.queue;

import com.pedrohk.queue.model.Dish;
import com.pedrohk.queue.model.Order;
import com.pedrohk.queue.service.QueueService;
import com.pedrohk.router.NotificationService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotificationIntegrationTest {

    @Test
    void shouldNotifyCustomerWhenOrderIsReady() throws InterruptedException {
        NotificationService notificationService = new NotificationService();
        QueueService queueService = new QueueService(1, notificationService);

        CountDownLatch readyLatch = new CountDownLatch(1);
        AtomicReference<String> finalStatus = new AtomicReference<>();

        notificationService.subscribe((order, status) -> {
            if ("READY".equals(status)) {
                finalStatus.set(status);
                readyLatch.countDown();
            }
        });

        Order order = new Order(1, "pedro@test.com", new Dish("Pizza", 10), LocalDateTime.now());

        queueService.placeOrder(order);
        queueService.completeOrder();

        boolean receivedReady = readyLatch.await(2, TimeUnit.SECONDS);

        assertTrue(receivedReady, "O status READY não foi recebido a tempo");
        assertEquals("READY", finalStatus.get());
    }

    @Test
    void testAllStatusesReceived() throws InterruptedException {
        NotificationService notificationService = new NotificationService();
        QueueService queueService = new QueueService(1, notificationService);

        ConcurrentLinkedQueue<String> receivedStatuses = new ConcurrentLinkedQueue<>();
        CountDownLatch latch = new CountDownLatch(2);

        notificationService.subscribe((order, status) -> {
            receivedStatuses.add(status);
            latch.countDown();
        });

        Order order = new Order(2, "test@test.com", new Dish("Pasta", 5), LocalDateTime.now());
        queueService.placeOrder(order);
        queueService.completeOrder();

        assertTrue(latch.await(2, TimeUnit.SECONDS));
        assertTrue(receivedStatuses.contains("RECEIVED"));
        assertTrue(receivedStatuses.contains("READY"));
    }
}
