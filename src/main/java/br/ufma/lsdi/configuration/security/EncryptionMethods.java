package br.ufma.lsdi.configuration.security;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionMethods implements Serializable {

    public static String MD5(String frase) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(frase.getBytes());
            byte[] md5 = md.digest();
            return encrypt(md5);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String SHA1(String frase) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(frase.getBytes());
            byte[] sha1 = md.digest();
            return encrypt(sha1);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String SHA256(String frase) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(frase.getBytes());
            byte[] sha256 = md.digest();
            return encrypt(sha256);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private static String encrypt(byte[] bytes) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int top = ((bytes[i] >> 4) & 0xf) << 4;
            int lower = bytes[i] & 0xf;
            if (top == 0)
                string.append('0');
            string.append(Integer.toHexString(top | lower));
        }
        return string.toString();
    }
}

