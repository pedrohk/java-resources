package com.pedrohk.social.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record Comment(UUID id, String author, String text, LocalDateTime timestamp) {
    public Comment(String author, String text) {
        this(UUID.randomUUID(), author, text, LocalDateTime.now());
    }
}
