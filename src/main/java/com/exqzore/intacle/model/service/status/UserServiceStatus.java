package com.exqzore.intacle.model.service.status;

import com.exqzore.intacle.model.entity.User;

public class UserServiceStatus {
    private boolean isInvalidParams;
    private boolean isLoginBusy;
    private boolean isNotActivated;
    private User user;

//    private static final UserServiceStatus instance = new UserServiceStatus();
//
//    private UserServiceStatus() {
//    }
//
//    public static UserServiceStatus getInstance() {
//        return instance;
//    }

    public UserServiceStatus() {
    }

    public UserServiceStatus(boolean isInvalidParams, boolean isLoginBusy, boolean isNotActivated, User user) {
        this.isInvalidParams = isInvalidParams;
        this.isLoginBusy = isLoginBusy;
        this.isNotActivated = isNotActivated;
        this.user = user;
    }

    public boolean isGood() {
        return !isInvalidParams && !isLoginBusy && !isNotActivated;
    }

    public boolean isNotActivated() {
        return isNotActivated;
    }

    public void setNotActivated(boolean notActivated) {
        isNotActivated = notActivated;
    }

    public boolean isInvalidParams() {
        return isInvalidParams;
    }

    public void setInvalidParams(boolean invalidParams) {
        isInvalidParams = invalidParams;
    }

    public boolean isLoginBusy() {
        return isLoginBusy;
    }

    public void setLoginBusy(boolean loginBusy) {
        isLoginBusy = loginBusy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
