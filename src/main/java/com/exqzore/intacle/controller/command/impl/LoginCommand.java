package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.RequestParameter;
import com.exqzore.intacle.controller.WebPageRequest;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.service.impl.UserServiceImpl;
import com.exqzore.intacle.model.service.status.UserServiceStatus;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final UserServiceImpl userService = UserServiceImpl.getInstance();

    private static final String IS_INVALID_PARAMS = "is_invalid_params";
    private static final String IS_USER_LOGIN_ERROR = "is_user_login_error";
    private static final String IS_NOT_ACTIVATED = "is_not_activated";
    private static final String IS_LOGIN = "is_login";
    private static final String USER = "user";

    @Override
    public String execute(HttpServletRequest request) {
        String resultPage;
        String login = request.getParameter(RequestParameter.LOGIN);
        String password = request.getParameter(RequestParameter.PASSWORD);
        HttpSession session = request.getSession();
        session.setAttribute(IS_INVALID_PARAMS, false);
        session.setAttribute(IS_USER_LOGIN_ERROR, false);
        session.setAttribute(IS_NOT_ACTIVATED, false);
        try {
            UserServiceStatus userServiceStatus = userService.login(login, password);
            if (userServiceStatus.isGood()) {
                User user = userServiceStatus.getUser();
                session.setAttribute(IS_LOGIN, true);
                session.setAttribute(USER, user);
                resultPage = String.format(WebPageRequest.SHOW_PROFILE, user.getLogin());
            } else {
                if (userServiceStatus.isInvalidParams()) {
                    session.setAttribute(IS_INVALID_PARAMS, true);
                } else {
                    session.setAttribute(IS_NOT_ACTIVATED, true);
                }
                resultPage = WebPageRequest.GO_LOGIN_PAGE;
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception.getMessage());
            session.setAttribute(IS_USER_LOGIN_ERROR, true);
            resultPage = WebPageRequest.GO_LOGIN_PAGE;
        }
        return resultPage;
    }
}
