package com.pedrohk.queue.service;

import com.pedrohk.queue.model.Dish;
import com.pedrohk.queue.model.Order;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class QueueService {
    private final Deque<Order> queue = new ConcurrentLinkedDeque<>();
    private final int chefCapacity;

    public QueueService(int chefCapacity) {
        this.chefCapacity = chefCapacity;
    }

    public void placeOrder(Order order) {
        queue.addLast(order);
    }

    public int estimateWaitTimeMinutes(Dish newDish) {
        int totalQueueTime = queue.stream()
                .mapToInt(o -> o.dish().basePreparationTimeMinutes())
                .sum();

        return (totalQueueTime / chefCapacity) + newDish.basePreparationTimeMinutes();
    }

    public void completeOrder() {
        queue.pollFirst();
    }
}
