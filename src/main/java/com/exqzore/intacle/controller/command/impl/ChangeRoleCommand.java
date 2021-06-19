package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.ErrorPageAttribute;
import com.exqzore.intacle.controller.WebPagePath;
import com.exqzore.intacle.controller.WebPageRequest;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.entity.User;
import com.exqzore.intacle.entity.UserRole;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.service.UserService;
import com.exqzore.intacle.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ChangeRoleCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final UserService userService = UserServiceImpl.getInstance();

    private static final String USER_ID = "userId";
    private static final String USER_ROLE = "user_role-";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String pagePath;
        String userIdString = request.getParameter(USER_ID);
        long userId = Long.parseLong(userIdString);
        String userRole = request.getParameter(USER_ROLE + userIdString);
        try {
            if (userService.changeRole(userRole, userId)) {
                pagePath = WebPageRequest.GO_ADMIN_PANEL;
            } else {
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                pagePath = WebPagePath.ERROR_PAGE;
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            request.setAttribute(ErrorPageAttribute.EXCEPTION, exception);
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            pagePath = WebPagePath.ERROR_PAGE;
        }
        return pagePath;
    }

    @Override
    public List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.ADMIN);
    }
}
