package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.entity.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ChangeLanguageCommand implements Command {
    private static final String LOCALE = "locale";
    private final static String PREVIOUS_PAGE = "previous_page";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String locale = request.getParameter(LOCALE);
        HttpSession session = request.getSession();
        session.setAttribute(LOCALE, locale);
        return (String) session.getAttribute(PREVIOUS_PAGE);
    }

    @Override
    public List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.values());
    }


}
