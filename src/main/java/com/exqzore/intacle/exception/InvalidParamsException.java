package com.exqzore.intacle.exception;

public class InvalidParamsException extends ServiceException {
    public InvalidParamsException() {
        super();
    }

    public InvalidParamsException(String message) {
        super(message);
    }

    public InvalidParamsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParamsException(Throwable cause) {
        super(cause);
    }
}
