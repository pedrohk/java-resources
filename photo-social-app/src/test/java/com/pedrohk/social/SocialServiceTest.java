package com.pedrohk.social;

import com.pedrohk.social.service.SocialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SocialServiceTest {
    private SocialService service;

    @BeforeEach
    void setup() {
        service = new SocialService();
    }

    @Test
    void shouldPublishAndRetrievePost() {
        var post = service.publishPhoto("pedro", "http://cloud.com", Set.of("java", "tech"));

        var timeline = service.getTimeline();
        assertEquals(1, timeline.size());
        assertEquals("pedro", timeline.getFirst().owner());
        assertTrue(timeline.getFirst().tags().contains("java"));
    }

    @Test
    void shouldAddCommentsToPost() {
        var post = service.publishPhoto("dev", "url", Set.of());
        service.addComment(post.id(), "user2", "Great photo!");

        assertEquals(1, service.getTimeline().getFirst().comments().size());
        assertEquals("Great photo!", service.getTimeline().getFirst().comments().getFirst().text());
    }

    @Test
    void shouldFilterByTag() {
        service.publishPhoto("p1", "u1", Set.of("summer"));
        service.publishPhoto("p2", "u2", Set.of("winter"));

        var summerPosts = service.findByTag("summer");
        assertEquals(1, summerPosts.size());
        assertEquals("p1", summerPosts.getFirst().owner());
    }

    @Test
    void shouldEnforceSecurityOnDelete() {
        var post = service.publishPhoto("owner", "url", Set.of());

        assertThrows(SecurityException.class, () -> service.deletePost(post.id(), "hacker"));
        assertDoesNotThrow(() -> service.deletePost(post.id(), "owner"));
        assertTrue(service.getTimeline().isEmpty());
    }

    @Test
    void timelineShouldBeSortedByDate() throws InterruptedException {
        service.publishPhoto("user1", "url1", Set.of());
        Thread.sleep(10);
        service.publishPhoto("user2", "url2", Set.of());

        var timeline = service.getTimeline();
        assertEquals("user2", timeline.get(0).owner());
        assertEquals("user1", timeline.get(1).owner());
    }
}
