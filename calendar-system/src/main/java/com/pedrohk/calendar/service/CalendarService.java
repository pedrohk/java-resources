package com.pedrohk.calendar.service;

import com.pedrohk.calendar.model.Meeting;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CalendarService {
    private final Map<String, List<Meeting>> userCalendars = new ConcurrentHashMap<>();

    public Meeting bookMeeting(String title, LocalDateTime start, LocalDateTime end, Set<String> participants) {
        if (!start.isBefore(end)) throw new IllegalArgumentException("Start must be before end");

        for (String participant : participants) {
            boolean hasConflict = userCalendars.getOrDefault(participant, List.of()).stream()
                    .anyMatch(m -> m.overlaps(start, end));
            if (hasConflict) throw new IllegalStateException("Conflict for participant: " + participant);
        }

        Meeting meeting = new Meeting(UUID.randomUUID(), title, start, end, participants);
        for (String participant : participants) {
            userCalendars.computeIfAbsent(participant, k -> new ArrayList<>()).add(meeting);
        }
        return meeting;
    }

    public void removeMeeting(String user, UUID meetingId) {
        List<Meeting> meetings = userCalendars.get(user);
        if (meetings != null) {
            meetings.removeIf(m -> m.id().equals(meetingId));
        }
    }

    public List<Meeting> listMeetings(String user) {
        return userCalendars.getOrDefault(user, List.of()).stream()
                .sorted(Comparator.comparing(Meeting::start))
                .toList();
    }

    public Optional<LocalDateTime> suggestBestTime(String user1, String user2, Duration duration, LocalDateTime searchStart, LocalDateTime searchEnd) {
        LocalDateTime current = searchStart;
        Set<String> pair = Set.of(user1, user2);

        while (current.plus(duration).isBefore(searchEnd)) {
            final LocalDateTime slotStart = current;
            final LocalDateTime slotEnd = current.plus(duration);

            boolean conflict = pair.stream()
                    .flatMap(u -> userCalendars.getOrDefault(u, List.of()).stream())
                    .anyMatch(m -> m.overlaps(slotStart, slotEnd));

            if (!conflict) return Optional.of(slotStart);
            current = current.plusMinutes(30);
        }
        return Optional.empty();
    }
}
