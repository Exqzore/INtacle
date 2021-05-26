package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public enum GoToHomePageCommand implements Command {
    INSTANCE;

    @Override
    public String execute(HttpServletRequest request) {
        return "/jsp/home_page.jsp";
    }
}
