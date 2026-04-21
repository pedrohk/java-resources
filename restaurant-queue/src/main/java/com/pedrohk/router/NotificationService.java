package com.pedrohk.router;

import com.pedrohk.queue.model.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationService {
    private final List<NotificationListener> listeners = new ArrayList<>();
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    public void subscribe(NotificationListener listener) {
        listeners.add(listener);
    }

    public void notify(Order order, String status) {
        for (NotificationListener listener : listeners) {
            executor.submit(() -> listener.onOrderStatusChanged(order, status));
        }
    }
}
