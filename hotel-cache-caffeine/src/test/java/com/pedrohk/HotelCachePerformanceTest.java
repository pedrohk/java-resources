package com.pedrohk;

import com.pedrohk.service.RoomAvailabilityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HotelCachePerformanceTest {

    @Autowired
    private RoomAvailabilityService service;

    @Test
    void testCachePerformance() {
        int roomNumber = 101;

        StopWatch stopWatch = new StopWatch();

        stopWatch.start("First Call - No Cache");
        String result1 = service.checkAvailability(roomNumber);
        stopWatch.stop();
        long firstCallTime = stopWatch.getLastTaskTimeMillis();

        stopWatch.start("Second Call - From Cache");
        String result2 = service.checkAvailability(roomNumber);
        stopWatch.stop();
        long secondCallTime = stopWatch.getLastTaskTimeMillis();

        assertThat(result1).isEqualTo(result2);
        assertThat(firstCallTime).isGreaterThanOrEqualTo(2000);
        assertThat(secondCallTime).isLessThan(100);

        System.out.println("First call: " + firstCallTime + "ms");
        System.out.println("Second call: " + secondCallTime + "ms");
    }
}
