package com.exqzore.intacle.model.validator;

public class ActivationCodeValidator {
    private final static String EMAIL_PATTERN = "[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}";

    private ActivationCodeValidator() {
    }

    public static boolean checkActivationCode(String activationCode) {
        boolean result = false;
        if (activationCode != null && !activationCode.isEmpty()) {
            result = activationCode.matches(EMAIL_PATTERN);
        }
        return result;
    }
}
