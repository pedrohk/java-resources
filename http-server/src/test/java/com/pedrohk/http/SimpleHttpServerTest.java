package com.pedrohk.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleHttpServerTest {
    private static final int PORT = 8081;
    private static SimpleHttpServer server;

    @BeforeAll
    static void setup() {
        server = new SimpleHttpServer(PORT);
        server.get("/hello", () -> "Hello World");
        server.get("/status", () -> "System Active");

        Executors.newVirtualThreadPerTaskExecutor().submit(() -> {
            try {
                server.start();
            } catch (Exception ignored) {
            }
        });

        waitForServer();
    }

    private static void waitForServer() {
        int attempts = 0;
        while (attempts < 10) {
            try (var s = new java.net.Socket("localhost", PORT)) {
                return;
            } catch (Exception e) {
                attempts++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    @Test
    void shouldReturnOkForRegisteredRoute() throws Exception {
        URL url = new URL("http://localhost:" + PORT + "/hello");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        assertEquals(200, connection.getResponseCode());

        try (var in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            assertEquals("Hello World", in.readLine());
        }
    }

    @Test
    void shouldReturnNotFoundForUnknownRoute() throws Exception {
        URL url = new URL("http://localhost:" + PORT + "/unknown");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        assertEquals(404, connection.getResponseCode());
    }

    @Test
    void shouldHandleConcurrentRequests() throws Exception {
        int requests = 50;
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var countdown = new java.util.concurrent.CountDownLatch(requests);

        for (int i = 0; i < requests; i++) {
            executor.submit(() -> {
                try {
                    URL url = new URL("http://localhost:" + PORT + "/status");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    if (conn.getResponseCode() == 200) {
                        countdown.countDown();
                    }
                } catch (Exception ignored) {
                }
            });
        }

        assertTrue(countdown.await(5, java.util.concurrent.TimeUnit.SECONDS));
    }
}
