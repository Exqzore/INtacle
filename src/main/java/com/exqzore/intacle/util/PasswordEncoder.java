package com.exqzore.intacle.util;

import com.exqzore.intacle.exception.PasswordEncoderException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {
    private final static Logger logger = LogManager.getLogger(PasswordEncoder.class);

    private final static String ALGORITHM = "SHA-1";

    private PasswordEncoder() {
    }

    public static String encode(String password) throws PasswordEncoderException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            byte[] encoded = messageDigest.digest(password.getBytes());
            return new String(encoded);
        } catch (NoSuchAlgorithmException exception) {
            logger.log(Level.ERROR, "Password encode error" , exception);
            throw new PasswordEncoderException(exception);
        }
    }
}
