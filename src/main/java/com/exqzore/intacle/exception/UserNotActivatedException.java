package com.exqzore.intacle.exception;

public class UserNotActivatedException extends ServiceException {
    public UserNotActivatedException() {
        super();
    }

    public UserNotActivatedException(String message) {
        super(message);
    }

    public UserNotActivatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotActivatedException(Throwable cause) {
        super(cause);
    }
}
