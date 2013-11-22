package com.yipeipei.crypto;

import java.nio.charset.Charset;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import edu.princeton.cs.introcs.StdOut;

public class AES {

    public static byte[] encrypt(String key, String value)
            throws GeneralSecurityException {
        byte[] raw = key.getBytes();
        if (raw.length != 16) {
            throw new IllegalArgumentException("Invalid key size.");
        }

        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(
                new byte[16]));
        return cipher.doFinal(value.getBytes());
    }

    public static String decrypt(String key, byte[] encrypted)
            throws GeneralSecurityException {

        byte[] raw = key.getBytes();
        if (raw.length != 16) {
            throw new IllegalArgumentException("Invalid key size.");
        }
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(
                new byte[16]));
        byte[] original = cipher.doFinal(encrypted);

        return new String(original);
    }

    public static void main(String[] args) {
        try {

            String key = "ThisIsASecretKey";
            byte[] ciphertext = encrypt(key, "00000000000000123450123456");
            StdOut.println("ciphertext: " + ciphertext.hashCode());
            System.out.println("decrypted value:" + (decrypt(key, ciphertext)));

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}