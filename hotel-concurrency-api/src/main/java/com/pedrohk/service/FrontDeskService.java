package com.pedrohk.service;

import com.pedrohk.model.HotelTask;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FrontDeskService {

    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public Future<String> executeCheckIn(HotelTask task) {
        return executor.submit(() -> {
            Thread.sleep(task.durationMillis());
            return "Check-in completed for: " + task.description();
        });
    }

    public void executeCleaning(HotelTask task) {
        executor.execute(() -> {
            try {
                Thread.sleep(task.durationMillis());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    public List<String> processMultipleCheckIns(List<HotelTask> tasks) throws InterruptedException {
        List<Callable<String>> callables = tasks.stream()
                .map(task -> (Callable<String>) () -> {
                    Thread.sleep(task.durationMillis());
                    return task.id();
                })
                .toList();

        return executor.invokeAll(callables).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        return "Error";
                    }
                })
                .toList();
    }

    public void shutdown() {
        executor.shutdown();
    }
}
