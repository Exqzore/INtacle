package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.WebPagePath;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.service.UserService;
import com.exqzore.intacle.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ActivateUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final UserService userService = UserServiceImpl.getInstance();

    private static final String LOGIN = "login";
    private static final String ACTIVATION_CODE = "activation_code";
    private static final String IS_INVALID_ACTIVATE_PARAMS = "isInvalidActivateParams";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String login = request.getParameter(LOGIN);
        String activationCode = request.getParameter(ACTIVATION_CODE);
        session.setAttribute(IS_INVALID_ACTIVATE_PARAMS, false);
        try {
            boolean isActivated = userService.activate(login, activationCode);
            if (!isActivated) {
                session.setAttribute(IS_INVALID_ACTIVATE_PARAMS, true);
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            session.setAttribute(IS_INVALID_ACTIVATE_PARAMS, true);
        }
        return WebPagePath.ACTIVATION_PAGE;
    }
}
