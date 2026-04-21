package com.pedrohk.router;

import com.pedrohk.queue.model.Order;

public interface NotificationListener {
    void onOrderStatusChanged(Order order, String status);
}
