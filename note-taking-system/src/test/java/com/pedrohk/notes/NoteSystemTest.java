package com.pedrohk.notes;

import com.pedrohk.notes.model.Note;
import com.pedrohk.notes.service.NoteService;
import com.pedrohk.notes.service.SyncService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NoteSystemTest {
    private NoteService noteService;
    private SyncService syncService;

    @BeforeEach
    void setup() {
        noteService = new NoteService();
        syncService = new SyncService();
    }

    @Test
    void testCreateAndEditNote() {
        Note note = noteService.addNote("Title", "Content");
        Note updated = noteService.editNote(note.id(), "New Title", "New Content");

        assertEquals("New Title", updated.title());
        assertEquals(2L, updated.version());
        assertTrue(updated.lastModified().isAfter(note.lastModified()) || updated.lastModified().equals(note.lastModified()));
    }

    @Test
    void testDeleteNote() {
        Note note = noteService.addNote("To Delete", "...");
        noteService.deleteNote(note.id());
        assertTrue(noteService.getAllNotes().isEmpty());
    }

    @Test
    void testDeleteNonExistentThrowsException() {
        assertThrows(NoSuchElementException.class, () -> noteService.deleteNote(UUID.randomUUID()));
    }

    @Test
    void testSyncBidirectional() {
        NoteService clientA = new NoteService();
        NoteService clientB = new NoteService();

        Note note1 = clientA.addNote("A's Note", "From A");
        Note note2 = clientB.addNote("B's Note", "From B");

        syncService.sync(clientA, clientB);

        assertEquals(2, clientA.getAllNotes().size());
        assertEquals(2, clientB.getAllNotes().size());
    }

    @Test
    void testSyncConflictResolutionByVersion() {
        NoteService client = new NoteService();
        NoteService server = new NoteService();

        Note initial = client.addNote("Conflict", "V1");
        server.saveNote(initial);

        Note clientUpdate = client.editNote(initial.id(), "Conflict", "Client V2");

        syncService.sync(client, server);

        Note serverNote = server.getRawStore().get(initial.id());
        assertEquals("Client V2", serverNote.content());
        assertEquals(2L, serverNote.version());
    }

    @Test
    void testListAllIsSortedByDate() throws InterruptedException {
        noteService.addNote("Old", "...");
        Thread.sleep(10);
        noteService.addNote("New", "...");

        var notes = noteService.getAllNotes();
        assertEquals("New", notes.get(0).title());
    }
}
