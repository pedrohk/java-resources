package com.pedrohk;

import com.pedrohk.model.HotelTask;
import com.pedrohk.service.FrontDeskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ConcurrencyHotelApplication.class)
class HotelConcurrencyTest {

    private final FrontDeskService service = new FrontDeskService();

    @AfterEach
    void tearDown() {
        service.shutdown();
    }

    @Test
    void shouldExecuteAsyncCheckIn() throws Exception {
        HotelTask task = new HotelTask("1", "Guest Pedro", 500);
        Future<String> future = service.executeCheckIn(task);

        assertThat(future.isDone()).isFalse();

        String result = future.get(1, TimeUnit.SECONDS);
        assertThat(result).contains("Guest Pedro");
    }

    @Test
    void shouldProcessBatchTasksConcurrently() throws Exception {
        List<HotelTask> tasks = List.of(
                new HotelTask("T1", "Guest A", 200),
                new HotelTask("T2", "Guest B", 200),
                new HotelTask("T3", "Guest C", 200)
        );

        long start = System.currentTimeMillis();
        List<String> results = service.processMultipleCheckIns(tasks);
        long end = System.currentTimeMillis();

        assertThat(results).containsExactlyInAnyOrder("T1", "T2", "T3");
        assertThat(end - start).isLessThan(500);
    }
}
