package com.pedrohk;

import com.pedrohk.repository.RoomRepository;
import com.pedrohk.service.CheckInService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HotelIocTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    @DisplayName("Verify if all Beans are correctly registered in the Spring Context")
    void verifyContextBeans() {
        assertThat(context.containsBean("checkInService")).isTrue();
        assertThat(context.containsBean("roomRepository")).isTrue();
        assertThat(context.containsBean("notificationService")).isTrue();

        CheckInService serviceFromContext = context.getBean(CheckInService.class);
        assertThat(serviceFromContext).isSameAs(checkInService);
    }

    @Test
    @DisplayName("Successful Check-in should update room status and return true")
    void testSuccessfulCheckIn() {
        boolean result = checkInService.performCheckIn(101, "Pedro Silva");

        assertThat(result).isTrue();
        assertThat(roomRepository.isAvailable(101)).isFalse();
    }

    @Test
    @DisplayName("Check-in in occupied room should return false")
    void testOccupiedRoomCheckIn() {
        boolean result = checkInService.performCheckIn(102, "John Doe");

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Verify Dependency Injection Singleton behavior")
    void testSingletonScope() {
        RoomRepository anotherRepoRef = context.getBean(RoomRepository.class);
        assertThat(roomRepository).isSameAs(anotherRepoRef);
    }
}
