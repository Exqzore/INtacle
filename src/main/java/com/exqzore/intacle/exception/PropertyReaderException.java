package com.exqzore.intacle.exception;

public class PropertyReaderException extends Exception {
    public PropertyReaderException() {
        super();
    }

    public PropertyReaderException(String message) {
        super(message);
    }

    public PropertyReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertyReaderException(Throwable cause) {
        super(cause);
    }
}
