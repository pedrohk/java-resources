package com.pedrohk.queue.service;

import com.pedrohk.queue.model.Dish;
import com.pedrohk.queue.model.Order;
import com.pedrohk.router.NotificationService;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class QueueService {
    private final Deque<Order> queue = new ConcurrentLinkedDeque<>();
    private final int chefCapacity;
    private final NotificationService notificationService;

    public QueueService(int chefCapacity, NotificationService notificationService) {
        this.chefCapacity = chefCapacity;
        this.notificationService = notificationService;
    }

    public void placeOrder(Order order) {
        queue.addLast(order);
        notificationService.notify(order, "RECEIVED");
    }

    public void completeOrder() {
        Order completed = queue.pollFirst();
        if (completed != null) {
            notificationService.notify(completed, "READY");
        }
    }

    public int estimateWaitTimeMinutes(Dish newDish) {
        int currentQueueTime = queue.stream()
                .mapToInt(o -> o.dish().basePreparationTimeMinutes())
                .sum();

        return (currentQueueTime + newDish.basePreparationTimeMinutes()) / chefCapacity;
    }

}
