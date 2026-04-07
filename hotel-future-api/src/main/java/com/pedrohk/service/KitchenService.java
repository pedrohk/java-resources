package com.pedrohk.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class KitchenService {

    private final ExecutorService kitchenStaff = Executors.newFixedThreadPool(2);

    public Future<String> prepareOrder(String dishName, int preparationTime) {
        Callable<String> task = () -> {
            Thread.sleep(preparationTime);
            return "Dish " + dishName + " is ready!";
        };
        return kitchenStaff.submit(task);
    }

    public void closeKitchen() {
        kitchenStaff.shutdown();
    }
}
