package com.pedrohk.stress;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpStressTesterTest {
    private static final int PORT = 9999;
    private static final String BASE_URL = "http://localhost:" + PORT + "/test";
    private static HttpServer server;

    @BeforeAll
    static void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(PORT), 2000);
        server.createContext("/test", exchange -> {
            String response = "OK";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        server.start();
    }

    @AfterAll
    static void stopServer() {
        server.stop(0);
    }

    @Test
    void testStressLoad() {
        HttpStressTester tester = new HttpStressTester(10);
        int requests = 500;

        StressResult result = tester.run(BASE_URL, requests);

        assertEquals(requests, result.totalRequests());
        assertEquals(requests, result.successfulRequests());
        assertEquals(0, result.failedRequests());
        assertTrue(result.durationMs() > 0);
        assertTrue(result.requestsPerSecond() > 0);
    }

    @Test
    void testFailureHandling() {
        HttpStressTester tester = new HttpStressTester(1);
        StressResult result = tester.run("http://localhost:" + PORT + "/invalid", 10);

        assertEquals(10, result.totalRequests());
        assertEquals(10, result.failedRequests());
    }

    @Test
    void testConnectionErrorHandling() {
        HttpStressTester tester = new HttpStressTester(1);
        StressResult result = tester.run("http://localhost:1", 5);

        assertEquals(5, result.failedRequests());
    }

    @Test
    void testHighConcurrencyStability() {
        HttpStressTester tester = new HttpStressTester(100);
        int highLoad = 2000;

        StressResult result = tester.run(BASE_URL, highLoad);

        assertEquals(highLoad, result.successfulRequests());
    }
}
