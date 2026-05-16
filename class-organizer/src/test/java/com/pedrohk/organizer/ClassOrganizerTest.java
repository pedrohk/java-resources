package com.pedrohk.organizer;

import com.pedrohk.organizer.model.DayOfWeek;
import com.pedrohk.organizer.model.SchoolClass;
import com.pedrohk.organizer.model.TimeSlot;
import com.pedrohk.organizer.service.ClassOrganizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClassOrganizerTest {
    private ClassOrganizer organizer;

    @BeforeEach
    void setup() {
        organizer = new ClassOrganizer();
    }

    @Test
    void shouldAddClassSuccessfully() {
        TimeSlot slot = new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(9, 30));
        SchoolClass c = organizer.addClass("Math", "Group A", slot);

        assertNotNull(c.id());
        assertEquals(1, organizer.getWeeklySchedule().size());
    }

    @Test
    void shouldThrowExceptionOnConflict() {
        TimeSlot slot1 = new TimeSlot(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(11, 0));
        TimeSlot slot2 = new TimeSlot(DayOfWeek.TUESDAY, LocalTime.of(10, 30), LocalTime.of(11, 30));

        organizer.addClass("History", "Group B", slot1);

        assertThrows(IllegalStateException.class, () ->
                organizer.addClass("Science", "Group C", slot2)
        );
    }

    @Test
    void shouldAllowClassesOnDifferentDaysAtSameTime() {
        TimeSlot mon = new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0));
        TimeSlot tue = new TimeSlot(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(10, 0));

        assertDoesNotThrow(() -> {
            organizer.addClass("Math", "A", mon);
            organizer.addClass("Physics", "B", tue);
        });
    }

    @Test
    void shouldRemoveClassCorrectly() {
        SchoolClass c = organizer.addClass("Arts", "D", new TimeSlot(DayOfWeek.FRIDAY, LocalTime.of(14, 0), LocalTime.of(15, 0)));
        organizer.removeClass(c.id());
        assertTrue(organizer.getWeeklySchedule().isEmpty());
    }

    @Test
    void shouldThrowWhenRemovingNonExistent() {
        assertThrows(NoSuchElementException.class, () -> organizer.removeClass(UUID.randomUUID()));
    }

    @Test
    void shouldReturnSortedSchedule() {
        organizer.addClass("Late", "G1", new TimeSlot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(18, 0)));
        organizer.addClass("Early", "G2", new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(9, 0)));
        organizer.addClass("Mid", "G3", new TimeSlot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(11, 0)));

        List<SchoolClass> schedule = organizer.getWeeklySchedule();

        assertEquals("Early", schedule.get(0).subject());
        assertEquals("Mid", schedule.get(1).subject());
        assertEquals("Late", schedule.get(2).subject());
    }
}
