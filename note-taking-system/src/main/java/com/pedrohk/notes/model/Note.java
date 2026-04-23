package com.pedrohk.notes.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record Note(UUID id, String title, String content, LocalDateTime lastModified, long version) {
    public Note(String title, String content) {
        this(UUID.randomUUID(), title, content, LocalDateTime.now(), 1L);
    }

    public Note update(String newTitle, String newContent) {
        return new Note(this.id, newTitle, newContent, LocalDateTime.now(), this.version + 1);
    }
}
