package com.pedrohk.organizer.model;

import java.time.LocalTime;

public record TimeSlot(DayOfWeek day, LocalTime start, LocalTime end) {
    public boolean overlaps(TimeSlot other) {
        if (this.day != other.day) return false;
        return this.start.isBefore(other.end) && other.start.isBefore(this.end);
    }
}
