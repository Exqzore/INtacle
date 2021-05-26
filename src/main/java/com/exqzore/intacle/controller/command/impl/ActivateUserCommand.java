package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.RequestParameter;
import com.exqzore.intacle.controller.WebPagePath;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.service.impl.UserServiceImpl;
import com.exqzore.intacle.model.service.status.UserServiceStatus;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActivateUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final UserServiceImpl userService = UserServiceImpl.getInstance();

    private static final String IS_INVALID_ACTIVATE_PARAMS = "is_invalid_activate_params";

    @Override
    public String execute(HttpServletRequest request) {
        String resultPage;
        HttpSession session = request.getSession();
        String login = request.getParameter(RequestParameter.LOGIN);
        String activationCode = request.getParameter(RequestParameter.ACTIVATION_CODE);
        session.setAttribute(IS_INVALID_ACTIVATE_PARAMS, false);
        try {
            UserServiceStatus userServiceStatus = userService.activate(login, activationCode);
            if (!userServiceStatus.isGood()) {
                session.setAttribute(IS_INVALID_ACTIVATE_PARAMS, true);
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception.getMessage());
            session.setAttribute(IS_INVALID_ACTIVATE_PARAMS, true);
        }
        resultPage = WebPagePath.ACTIVATION_PAGE;
        return resultPage;
    }
}
