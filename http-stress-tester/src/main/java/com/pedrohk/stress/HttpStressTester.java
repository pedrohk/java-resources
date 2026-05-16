package com.pedrohk.stress;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

public class HttpStressTester {
    private final HttpClient client;
    private final ExecutorService executor;

    public HttpStressTester(int virtualThreads) {
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
        this.client = HttpClient.newBuilder()
                .executor(executor)
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    public StressResult run(String url, int totalRequests) {
        LongAdder success = new LongAdder();
        LongAdder fail = new LongAdder();

        long startTime = System.currentTimeMillis();

        CompletableFuture<?>[] futures = new CompletableFuture[totalRequests];
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        for (int i = 0; i < totalRequests; i++) {
            futures[i] = client.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                    .thenAccept(res -> {
                        if (res.statusCode() >= 200 && res.statusCode() < 300) success.increment();
                        else fail.increment();
                    })
                    .exceptionally(ex -> {
                        fail.increment();
                        return null;
                    });
        }

        CompletableFuture.allOf(futures).join();
        long endTime = System.currentTimeMillis();

        return new StressResult(totalRequests, success.sum(), fail.sum(), endTime - startTime);
    }
}
