package com.pedrohk;

import com.pedrohk.service.HotelManagerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = HotelLockApplication.class)
class HotelLockTest {

    @Test
    void shouldHandleConcurrentAccess() throws InterruptedException {
        HotelManagerService service = new HotelManagerService();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        AtomicInteger successReservations = new AtomicInteger(0);

        for (int i = 0; i < 100; i++) {
            executor.execute(service::incrementGuestCount);
            executor.execute(() -> {
                if (service.reserveRoom101()) {
                    successReservations.incrementAndGet();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        assertThat(service.getGuestCount()).isEqualTo(100);
        assertThat(successReservations.get()).isEqualTo(1);
    }
}
