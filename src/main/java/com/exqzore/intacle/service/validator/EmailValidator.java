package com.exqzore.intacle.service.validator;

public class EmailValidator {
    private final static String EMAIL_PATTERN = "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,100})";

    private EmailValidator() {
    }

    public static boolean checkEmail(String email) {
        boolean result = false;
        if (email != null && !email.isEmpty()) {
            result = email.matches(EMAIL_PATTERN);
        }
        return result;
    }
}
