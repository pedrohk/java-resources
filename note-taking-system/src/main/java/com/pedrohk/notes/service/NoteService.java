package com.pedrohk.notes.service;

import com.pedrohk.notes.model.Note;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NoteService {
    private final Map<UUID, Note> localStore = new ConcurrentHashMap<>();

    public Note addNote(String title, String content) {
        Note note = new Note(title, content);
        localStore.put(note.id(), note);
        return note;
    }

    public void saveNote(Note note) {
        localStore.put(note.id(), note);
    }

    public Note editNote(UUID id, String title, String content) {
        Note existing = Optional.ofNullable(localStore.get(id))
                .orElseThrow(() -> new NoSuchElementException("Note not found"));
        Note updated = existing.update(title, content);
        localStore.put(id, updated);
        return updated;
    }

    public void deleteNote(UUID id) {
        if (!localStore.containsKey(id)) throw new NoSuchElementException("Note not found");
        localStore.remove(id);
    }

    public List<Note> getAllNotes() {
        return localStore.values().stream()
                .sorted(Comparator.comparing(Note::lastModified).reversed())
                .toList();
    }

    public Map<UUID, Note> getRawStore() {
        return localStore;
    }
}
