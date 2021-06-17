package com.exqzore.intacle.service.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {
    private final static String ALGORITHM = "SHA-1";

    private PasswordEncoder() {
    }

    public static String encode(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
        byte[] encoded = messageDigest.digest(password.getBytes());
        return new String(encoded);
    }
}
