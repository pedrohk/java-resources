import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

class JUnit5FeaturesTest {

    @BeforeAll
    static void setupAll() {
    }

    @BeforeEach
    void setup() {
    }

    @Test
    void shouldGroupAssertionsAndThrowException() {
        assertAll("user",
                () -> assertEquals("Admin", "Admin"),
                () -> assertFalse(1 > 10)
        );

        assertThrows(ArithmeticException.class, () -> {
            int result = 10 / 0;
        });
    }

    @Test
    void shouldCompleteExecutionWithinTimeout() {
        assertTimeout(Duration.ofMillis(100), () -> {
            Thread.sleep(20);
        });
    }

    @Test
    void shouldExecuteOnlyWhenAssumptionIsMet() {
        String env = System.getenv("ENV") != null ? System.getenv("ENV") : "DEV";
        assumingThat("DEV".equals(env), () -> {
            assertEquals(10, 5 + 5);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"apple", "banana", "cherry"})
    void shouldValidateMultipleStringInputs(String fruit) {
        assertNotNull(fruit);
    }

    @Test
    @Disabled
    void shouldBeIgnored() {
    }

    @RepeatedTest(3)
    void shouldRepeatThisTestThreeTimes() {
        assertTrue(true);
    }

    @Nested
    class CartOperations {
        @Test
        void shouldBeEmptyWhenCartIsCreated() {
            assertTrue(true);
        }
    }
}
