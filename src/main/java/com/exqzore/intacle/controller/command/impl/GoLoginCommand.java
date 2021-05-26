package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.WebPagePath;
import com.exqzore.intacle.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class GoLoginCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return WebPagePath.LOGIN_PAGE;
    }
}
