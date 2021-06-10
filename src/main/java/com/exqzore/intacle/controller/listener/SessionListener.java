package com.exqzore.intacle.controller.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final String USER = "user";
    private static final String REQUESTED_USER = "requestedUser";
    private static final String LOCALE = "locale";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(USER, null);
        session.setAttribute(REQUESTED_USER, null);
        session.setAttribute(LOCALE, "ru_RU");
    }
}
