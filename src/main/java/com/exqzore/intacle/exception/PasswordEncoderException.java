package com.exqzore.intacle.exception;

public class PasswordEncoderException extends Exception {
    public PasswordEncoderException() {
        super();
    }

    public PasswordEncoderException(String message) {
        super(message);
    }

    public PasswordEncoderException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordEncoderException(Throwable cause) {
        super(cause);
    }
}
