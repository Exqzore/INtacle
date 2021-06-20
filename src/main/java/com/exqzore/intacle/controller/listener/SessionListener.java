package com.exqzore.intacle.controller.listener;

import com.exqzore.intacle.controller.WebPagePath;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final String USER = "user";
    private static final String LOCALE = "locale";
    private static final String PREVIOUS_PAGE = "previous_page";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(USER, null);
        session.setAttribute(LOCALE, "ru_RU");
        session.setAttribute(PREVIOUS_PAGE, WebPagePath.LOGIN_PAGE);
    }
}
