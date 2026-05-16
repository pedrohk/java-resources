package com.pedrohk.router;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggerRouterTest {

    @Test
    void shouldBuildSyncConsoleLogger() {
        Logger logger = new LoggerBuilder().to(LogDestination.CONSOLE).build();
        assertFalse(logger instanceof AsyncLogger);
        logger.log("Sync Message Test");
    }

    @Test
    void shouldBuildAsyncElkLogger() {
        Logger logger = new LoggerBuilder().to(LogDestination.ELK).async().build();
        assertInstanceOf(AsyncLogger.class, logger);
    }

    @Test
    void verifyAsyncBehaviorWithVirtualThreads() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<String> captured = new AtomicReference<>();

        Logger mock = msg -> {
            captured.set(msg);
            latch.countDown();
        };

        AsyncLogger asyncLogger = new AsyncLogger(mock);
        asyncLogger.log("Async Execution Test");

        boolean completed = latch.await(2, TimeUnit.SECONDS);
        assertTrue(completed);
        assertEquals("Async Execution Test", captured.get());
    }

    @Test
    void testSwitchingDestinationsInBuilder() {
        Logger logger = new LoggerBuilder().to(LogDestination.FILESYSTEM).build();
        assertNotNull(logger);
        logger.log("FS Routing Test");
    }
}
