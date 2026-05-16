package com.pedrohk.router;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncLogger implements Logger {
    private final Logger delegate;
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    public AsyncLogger(Logger delegate) {
        this.delegate = delegate;
    }

    @Override
    public void log(String message) {
        executor.submit(() -> delegate.log(message));
    }
}
