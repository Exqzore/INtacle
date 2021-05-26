package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.controller.WebPagePath;

import javax.servlet.http.HttpServletRequest;

public class GoRegisterCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return WebPagePath.REGISTRATION_PAGE;
    }
}
