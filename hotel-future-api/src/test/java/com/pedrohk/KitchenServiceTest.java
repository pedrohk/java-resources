package com.pedrohk;

import com.pedrohk.service.KitchenService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = HotelFutureApplication.class)
class KitchenServiceTest {

    private final KitchenService service = new KitchenService();

    @AfterEach
    void tearDown() {
        service.closeKitchen();
    }

    @Test
    void shouldReturnResultFromKitchenTask() throws Exception {
        Future<String> pager = service.prepareOrder("Pasta", 500);

        assertThat(pager.isDone()).isFalse();

        String result = pager.get(1, TimeUnit.SECONDS);
        assertThat(result).isEqualTo("Dish Pasta is ready!");
        assertThat(pager.isDone()).isTrue();
    }
}
