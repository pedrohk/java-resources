package com.pedrohk.fileshare;

import com.pedrohk.fileshare.model.FileMetadata;
import com.pedrohk.fileshare.security.FileEncryptor;
import com.pedrohk.fileshare.service.FileShareManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileShareManagerTest {
    private final String testSecret = "my-super-secret-key";
    private FileShareManager manager;

    @BeforeEach
    void setup() {
        FileEncryptor encryptor = new FileEncryptor(testSecret);
        manager = new FileShareManager(encryptor);
    }

    @Test
    void testSaveAndRestoreEncryptedFile() throws Exception {
        String fileName = "test.txt";
        byte[] originalContent = "Hello World".getBytes();

        manager.save(fileName, originalContent);
        byte[] restoredContent = manager.restore(fileName);

        assertArrayEquals(originalContent, restoredContent);
        assertEquals(1, manager.listFiles().size());
    }

    @Test
    void testRestoreMissingFileThrowsException() {
        assertThrows(NoSuchElementException.class, () -> manager.restore("missing.dat"));
    }

    @Test
    void testDeleteFile() throws Exception {
        manager.save("temp.doc", "content".getBytes());
        manager.delete("temp.doc");

        assertTrue(manager.listFiles().isEmpty());
    }

    @Test
    void testSearchFunctionality() throws Exception {
        manager.save("report_v1.pdf", "data".getBytes());
        manager.save("invoice.pdf", "data".getBytes());
        manager.save("photo.jpg", "data".getBytes());

        List<FileMetadata> results = manager.search("pdf");
        assertEquals(2, results.size());
    }

    @Test
    void testEncryptionSecurity() throws Exception {
        String fileName = "secret.bin";
        byte[] content = "Top Secret Data".getBytes();

        manager.save(fileName, content);

        FileEncryptor wrongEncryptor = new FileEncryptor("wrong-key-123456");
        byte[] encryptedInStorage = manager.restore(fileName);

        byte[] rawEncrypted = new FileEncryptor(testSecret).encrypt(content);
        assertNotEquals(new String(content), new String(rawEncrypted));
    }
}
