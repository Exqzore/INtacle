package com.exqzore.intacle.model.validator;

public class PasswordValidator {
    private final static String PASSWORD_PATTERN = "[a-zA-Z\\d_-]{8,25}";

    private PasswordValidator() {
    }

    public static boolean checkPassword(String password) {
        boolean result = false;
        if (password != null && !password.isEmpty()) {
            result = password.matches(PASSWORD_PATTERN);
        }
        return result;
    }
}
