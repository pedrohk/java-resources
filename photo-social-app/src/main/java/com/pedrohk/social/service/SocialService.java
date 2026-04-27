package com.pedrohk.social.service;

import com.pedrohk.social.model.Comment;
import com.pedrohk.social.model.PhotoPost;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SocialService {
    private final Map<UUID, PhotoPost> posts = new ConcurrentHashMap<>();

    public PhotoPost publishPhoto(String owner, String url, Set<String> tags) {
        var post = new PhotoPost(owner, url, tags);
        posts.put(post.id(), post);
        return post;
    }

    public void addComment(UUID postId, String author, String text) {
        PhotoPost post = Optional.ofNullable(posts.get(postId))
                .orElseThrow(() -> new NoSuchElementException("Post not found"));
        post.comments().add(new Comment(author, text));
    }

    public List<PhotoPost> getTimeline() {
        return posts.values().stream()
                .sorted(Comparator.comparing(PhotoPost::createdAt).reversed())
                .toList();
    }

    public List<PhotoPost> findByTag(String tag) {
        return posts.values().stream()
                .filter(p -> p.tags().contains(tag))
                .sorted(Comparator.comparing(PhotoPost::createdAt).reversed())
                .toList();
    }

    public void deletePost(UUID postId, String requester) {
        PhotoPost post = posts.get(postId);
        if (post != null && post.owner().equals(requester)) {
            posts.remove(postId);
        } else if (post != null) {
            throw new SecurityException("Only owner can delete");
        }
    }
}
