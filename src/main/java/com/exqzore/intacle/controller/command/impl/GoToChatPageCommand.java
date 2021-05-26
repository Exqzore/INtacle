package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public enum GoToChatPageCommand implements Command {
    INSTANCE;

    @Override
    public String execute(HttpServletRequest request) {
        return "/jsp/rt_chat.jsp";
    }
}
