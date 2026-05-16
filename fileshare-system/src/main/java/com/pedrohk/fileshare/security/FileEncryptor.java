package com.pedrohk.fileshare.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class FileEncryptor {
    private static final String ALGORITHM = "AES";
    private final SecretKeySpec keySpec;

    public FileEncryptor(String secret) {
        byte[] key = secret.getBytes();
        this.keySpec = new SecretKeySpec(java.util.Arrays.copyOf(key, 16), ALGORITHM);
    }

    public byte[] encrypt(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return cipher.doFinal(data);
    }

    public byte[] decrypt(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return cipher.doFinal(data);
    }
}
