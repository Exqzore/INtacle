package com.exqzore.intacle.model.entity;

public class User {
    private static final String DEFAULT_AVATAR_PATH = "default_avatar.png";
    private static final String DEFAULT_USER_LEVEL = "user";

    private long id;
    private String login;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String activationCode;
    private String level = DEFAULT_USER_LEVEL;
    private String avatarPath = DEFAULT_AVATAR_PATH;

    public User() {
    }

    public User(long id) {
        this.id = id;
    }

    public User(long id, String login, String name, String surname, String email, String activationCode) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.activationCode = activationCode;
    }

    public User(long id, String login, String name, String surname, String email, String activationCode, String avatarPath) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.activationCode = activationCode;
        this.avatarPath = avatarPath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", login='").append(login).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", avatarPath='").append(avatarPath).append('\'');
        sb.append(", level='").append(level).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
