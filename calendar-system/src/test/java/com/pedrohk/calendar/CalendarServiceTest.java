package com.pedrohk.calendar;

import com.pedrohk.calendar.model.Meeting;
import com.pedrohk.calendar.service.CalendarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalendarServiceTest {
    private CalendarService service;

    @BeforeEach
    void setup() {
        service = new CalendarService();
    }

    @Test
    void shouldBookMeetingSuccessfully() {
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = start.plusHours(1);

        Meeting meeting = service.bookMeeting("Sync", start, end, Set.of("user1", "user2"));

        assertNotNull(meeting.id());
        assertEquals(1, service.listMeetings("user1").size());
    }

    @Test
    void shouldFailOnConflict() {
        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime end = start.plusHours(1);

        service.bookMeeting("Meeting 1", start, end, Set.of("user1"));

        assertThrows(IllegalStateException.class, () ->
                service.bookMeeting("Meeting 2", start.plusMinutes(30), end.plusMinutes(30), Set.of("user1"))
        );
    }

    @Test
    void shouldRemoveMeetingFromAllParticipants() {
        Meeting m = service.bookMeeting("Test", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(1), Set.of("u1", "u2"));
        UUID id = m.id();

        service.removeMeeting("u1", id);
        service.removeMeeting("u2", id);

        assertTrue(service.listMeetings("u1").isEmpty());
        assertTrue(service.listMeetings("u2").isEmpty());
    }

    @Test
    void shouldSuggestBestTimeForTwoPeople() {
        LocalDateTime searchStart = LocalDateTime.of(2025, 5, 1, 9, 0);
        LocalDateTime searchEnd = searchStart.plusHours(8);

        service.bookMeeting("U1 Busy", searchStart, searchStart.plusHours(1), Set.of("user1"));
        service.bookMeeting("U2 Busy", searchStart.plusHours(1), searchStart.plusHours(2), Set.of("user2"));

        Optional<LocalDateTime> suggestion = service.suggestBestTime("user1", "user2", Duration.ofHours(1), searchStart, searchEnd);

        assertTrue(suggestion.isPresent());
        assertEquals(searchStart.plusHours(2), suggestion.get());
    }
}
