package com.exqzore.intacle.service.validator;

public class LoginValidator {
    private final static String LOGIN_PATTERN = "[a-zA-Z][a-zA-Z0-9._-]{3,20}";

    private LoginValidator() {
    }

    public static boolean checkLogin(String login) {
        boolean result = false;
        if (login != null && !login.isEmpty()) {
            result = login.matches(LOGIN_PATTERN);
        }
        return result;
    }
}
