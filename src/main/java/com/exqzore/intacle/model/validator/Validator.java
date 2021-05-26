package com.exqzore.intacle.model.validator;

import java.util.Map;

public class Validator {
    public static final String LOGIN = "login";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String REPEAT_PASSWORD = "repeat_password";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";

    private Validator() {
    }

    public static boolean checkLogIn(String login, String password) {
        boolean result = true;
        if (login != null && !LoginValidator.checkLogin(login)) {
            result = false;
        }
        if (password != null && !PasswordValidator.checkPassword(password)) {
            result = false;
        }
        return result;
    }

    public static boolean checkActivateUser(String login, String activationCode) {
        boolean result = true;
        if (login != null && !LoginValidator.checkLogin(login)) {
            result = false;
        }
        if (activationCode != null && !ActivationCodeValidator.checkActivationCode(activationCode)) {
            result = false;
        }
        return result;
    }

    public static boolean checkRegistration(Map<String, String> parameters) {
        boolean result = true;
        String login = parameters.get(LOGIN);
        if (login != null && !LoginValidator.checkLogin(login)) {
            result = false;
        }
        String email = parameters.get(EMAIL);
        if (email != null && !EmailValidator.checkEmail(email)) {
            result = false;
        }
        String password = parameters.get(PASSWORD);
        if (password != null && !PasswordValidator.checkPassword(password)) {
            result = false;
        }
        String repeatPassword = parameters.get(REPEAT_PASSWORD);
        if (repeatPassword != null && !PasswordValidator.checkPassword(repeatPassword)) {
            result = false;
        }
        if (repeatPassword != null && !repeatPassword.equals(password)) {
            result = false;
        }
        String name = parameters.get(NAME);
        if (name != null && !name.isEmpty() && !NameValidator.checkName(name)) {
            result = false;
        }
        String surname = parameters.get(SURNAME);
        if (surname != null && !surname.isEmpty() && !NameValidator.checkName(surname)) {
            result = false;
        }
        return result;
    }
}
