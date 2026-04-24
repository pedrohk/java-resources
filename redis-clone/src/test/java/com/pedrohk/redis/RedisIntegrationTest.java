package com.pedrohk.redis;

import com.pedrohk.redis.client.RedisClient;
import com.pedrohk.redis.server.RedisServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RedisIntegrationTest {
    private static final int PORT = 6379;
    private static RedisClient client;

    @BeforeAll
    static void startServer() {
        Executors.newVirtualThreadPerTaskExecutor().submit(() -> {
            try {
                new RedisServer().start(PORT);
            } catch (IOException ignored) {
            }
        });

        int attempts = 0;
        while (attempts < 10) {
            try {
                client = new RedisClient("localhost", PORT);
                break;
            } catch (IOException e) {
                attempts++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    @AfterAll
    static void cleanup() throws IOException {
        if (client != null) client.close();
    }

    @Test
    void testStringCommands() throws IOException {
        assertEquals("OK", client.send("SET mykey hello"));
        assertEquals("hello", client.send("GET mykey"));
        assertEquals("OK", client.send("APPEND mykey world"));
        assertEquals("helloworld", client.send("GET mykey"));
    }

    @Test
    void testMapCommands() throws IOException {
        assertEquals("OK", client.send("HSET user:1 name pedro"));
        assertEquals("OK", client.send("HSET user:1 age 25"));
        assertEquals("pedro", client.send("HGET user:1 name"));

        String keys = client.send("HKEYS user:1");
        assertTrue(keys.contains("name") && keys.contains("age"));

        String vals = client.send("HVALS user:1");
        assertTrue(vals.contains("pedro") && vals.contains("25"));
    }
}
