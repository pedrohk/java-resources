package com.pedrohk.social.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record PhotoPost(
        UUID id,
        String owner,
        String imageUrl,
        Set<String> tags,
        List<Comment> comments,
        LocalDateTime createdAt
) {
    public PhotoPost(String owner, String imageUrl, Set<String> tags) {
        this(UUID.randomUUID(), owner, imageUrl, new HashSet<>(tags), new ArrayList<>(), LocalDateTime.now());
    }
}
