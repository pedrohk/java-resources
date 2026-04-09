package com.pedrohk;

import com.pedrohk.service.GuestSession;
import com.pedrohk.service.HotelDesk;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HotelScopesTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private HotelDesk deskA;

    @Autowired
    private HotelDesk deskB;

    @Test
    void testSingletonScope() {
        assertThat(deskA).isSameAs(deskB);

        deskA.incrementAndGetRequests();
        int requests = deskB.incrementAndGetRequests();

        assertThat(requests).isEqualTo(2);
    }

    @Test
    void testPrototypeScope() {
        GuestSession session1 = context.getBean(GuestSession.class);
        GuestSession session2 = context.getBean(GuestSession.class);

        assertThat(session1).isNotSameAs(session2);
        assertThat(session1.getSessionId()).isNotEqualTo(session2.getSessionId());
    }

    @Test
    void testStateIsolationInPrototype() {
        GuestSession session1 = context.getBean(GuestSession.class);
        GuestSession session2 = context.getBean(GuestSession.class);

        session1.setGuestName("Pedro");
        session2.setGuestName("Henrique");

        assertThat(session1.getGuestName()).isEqualTo("Pedro");
        assertThat(session2.getGuestName()).isEqualTo("Henrique");
        assertThat(session1.getGuestName()).isNotEqualTo(session2.getGuestName());
    }

    @Test
    void testSingletonStateConsistency() {
        HotelDesk deskFromContext = context.getBean(HotelDesk.class);
        int currentCount = deskFromContext.incrementAndGetRequests();

        HotelDesk anotherDeskRef = context.getBean(HotelDesk.class);
        assertThat(anotherDeskRef.incrementAndGetRequests()).isEqualTo(currentCount + 1);
    }
}
