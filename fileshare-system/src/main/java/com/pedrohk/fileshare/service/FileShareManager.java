package com.pedrohk.fileshare.service;

import com.pedrohk.fileshare.model.FileMetadata;
import com.pedrohk.fileshare.security.FileEncryptor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FileShareManager {
    private final Map<String, byte[]> storage = new ConcurrentHashMap<>();
    private final Map<String, FileMetadata> registry = new ConcurrentHashMap<>();
    private final FileEncryptor encryptor;

    public FileShareManager(FileEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    public void save(String fileName, byte[] content) throws Exception {
        if (fileName == null || fileName.isBlank()) throw new IllegalArgumentException("Invalid filename");
        byte[] encrypted = encryptor.encrypt(content);
        storage.put(fileName, encrypted);
        registry.put(fileName, new FileMetadata(fileName, content.length, LocalDateTime.now()));
    }

    public byte[] restore(String fileName) throws Exception {
        byte[] encryptedContent = Optional.ofNullable(storage.get(fileName))
                .orElseThrow(() -> new NoSuchElementException("File not found"));
        return encryptor.decrypt(encryptedContent);
    }

    public void delete(String fileName) {
        if (!storage.containsKey(fileName)) throw new NoSuchElementException("File not found");
        storage.remove(fileName);
        registry.remove(fileName);
    }

    public List<FileMetadata> listFiles() {
        return new ArrayList<>(registry.values());
    }

    public List<FileMetadata> search(String query) {
        return registry.values().stream()
                .filter(m -> m.fileName().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }
}
