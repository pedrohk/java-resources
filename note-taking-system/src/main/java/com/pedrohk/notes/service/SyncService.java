package com.pedrohk.notes.service;

import com.pedrohk.notes.model.Note;
import java.util.Map;
import java.util.UUID;

public class SyncService {
    public void sync(NoteService local, NoteService remote) {
        Map<UUID, Note> localData = local.getRawStore();
        Map<UUID, Note> remoteData = remote.getRawStore();

        localData.forEach((id, localNote) -> {
            Note remoteNote = remoteData.get(id);
            if (remoteNote == null || localNote.version() > remoteNote.version()) {
                remote.saveNote(localNote);
            }
        });

        remoteData.forEach((id, remoteNote) -> {
            Note localNote = localData.get(id);
            if (localNote == null || remoteNote.version() > localNote.version()) {
                local.saveNote(remoteNote);
            }
        });
    }
}
