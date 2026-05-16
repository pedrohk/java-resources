package com.pedrohk.calendar.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record Meeting(UUID id, String title, LocalDateTime start, LocalDateTime end, Set<String> participants) {

    public boolean overlaps(LocalDateTime otherStart, LocalDateTime otherEnd) {
        return start.isBefore(otherEnd) && otherStart.isBefore(end);
    }
}
