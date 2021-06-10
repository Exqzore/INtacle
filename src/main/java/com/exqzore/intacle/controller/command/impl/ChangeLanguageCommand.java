package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLanguageCommand implements Command {
    private static final String LOCALE = "locale";
    private final static String PREVIOUS_PAGE = "previous_page";

    @Override
    public String execute(HttpServletRequest request) {
        String locale = request.getParameter(LOCALE);
        HttpSession session = request.getSession();
        session.setAttribute(LOCALE, locale);
        return (String) session.getAttribute(PREVIOUS_PAGE);
    }
}
