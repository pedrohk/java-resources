package com.pedrohk.organizer.service;

import com.pedrohk.organizer.model.SchoolClass;
import com.pedrohk.organizer.model.TimeSlot;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClassOrganizer {
    private final Map<UUID, SchoolClass> classes = new ConcurrentHashMap<>();

    public SchoolClass addClass(String subject, String group, TimeSlot slot) {
        validateConflict(slot);
        SchoolClass schoolClass = new SchoolClass(UUID.randomUUID(), subject, group, slot);
        classes.put(schoolClass.id(), schoolClass);
        return schoolClass;
    }

    private void validateConflict(TimeSlot newSlot) {
        boolean conflict = classes.values().stream()
                .anyMatch(c -> c.slot().overlaps(newSlot));
        if (conflict) throw new IllegalStateException("Schedule conflict detected for " + newSlot);
    }

    public void removeClass(UUID id) {
        if (classes.remove(id) == null) throw new NoSuchElementException("Class not found");
    }

    public List<SchoolClass> getWeeklySchedule() {
        return classes.values().stream()
                .sorted(Comparator.comparing((SchoolClass c) -> c.slot().day())
                        .thenComparing(c -> c.slot().start()))
                .toList();
    }

    public List<SchoolClass> suggestOptimization() {
        return classes.values().stream()
                .sorted(Comparator.comparing((SchoolClass c) -> c.slot().day())
                        .thenComparing(c -> c.slot().start()))
                .toList();
    }
}
