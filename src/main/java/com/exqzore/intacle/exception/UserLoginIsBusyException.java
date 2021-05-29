package com.exqzore.intacle.exception;

public class UserLoginIsBusyException extends ServiceException {
    public UserLoginIsBusyException() {
        super();
    }

    public UserLoginIsBusyException(String message) {
        super(message);
    }

    public UserLoginIsBusyException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserLoginIsBusyException(Throwable cause) {
        super(cause);
    }
}
