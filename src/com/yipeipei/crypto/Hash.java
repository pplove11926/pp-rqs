package com.yipeipei.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class Hash {
    /**
     * An simple wrapper of MessageDigest. Allow MD5, SHA-1, SHA-256 on every
     * implementation of Java platform.
     * 
     * @param input
     * @param algorithm
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] digest(byte[] input, String algorithm) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return messageDigest.digest(input);
    }

    /**
     * convert byte array to Hex string.
     * 
     * @param array
     * @return
     */
    public static String byteArray2Hex(final byte[] array) {
        Formatter formatter = new Formatter();
        for (byte b : array) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }

}
