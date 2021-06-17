package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.WebPagePath;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.entity.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LogoutCommand implements Command {
    private static final String USER = "user";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute(USER, null);
        session.invalidate();
        return WebPagePath.LOGIN_PAGE;
    }

    @Override
    public List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.USER, UserRole.EDITOR, UserRole.ADMIN);
    }
}
