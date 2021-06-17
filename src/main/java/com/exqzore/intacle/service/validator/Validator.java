package com.exqzore.intacle.service.validator;

public class Validator {
    private Validator() {
    }

    public static boolean checkLogIn(String login, String password) {
        boolean result = login == null || LoginValidator.checkLogin(login);
        if (password != null && !PasswordValidator.checkPassword(password)) {
            result = false;
        }
        return result;
    }

    public static boolean checkActivateUser(String login, String activationCode) {
        boolean result = login == null || LoginValidator.checkLogin(login);
        if (activationCode != null && !ActivationCodeValidator.checkActivationCode(activationCode)) {
            result = false;
        }
        return result;
    }

    public static boolean checkRegistration(String login, String email, String password, String repeatPassword, String name, String surname) {
        boolean result = login == null || LoginValidator.checkLogin(login);
        if (email != null && !EmailValidator.checkEmail(email)) {
            result = false;
        }
        if (password != null && !PasswordValidator.checkPassword(password)) {
            result = false;
        }
        if (repeatPassword != null && !PasswordValidator.checkPassword(repeatPassword)) {
            result = false;
        }
        if (repeatPassword != null && !repeatPassword.equals(password)) {
            result = false;
        }
        if (name != null && !name.isEmpty() && !NameValidator.checkName(name)) {
            result = false;
        }
        if (surname != null && !surname.isEmpty() && !NameValidator.checkName(surname)) {
            result = false;
        }
        return result;
    }

    public static boolean checkEdit(String name, String surname) {
        boolean result = true;
        if (name != null && !name.isEmpty() && !NameValidator.checkName(name)) {
            result = false;
        }
        if (surname != null && !surname.isEmpty() && !NameValidator.checkName(surname)) {
            result = false;
        }
        return result;
    }
}
