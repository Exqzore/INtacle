package com.exqzore.intacle.controller.command;

import com.exqzore.intacle.entity.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response);

    default List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.values());
    }
}
