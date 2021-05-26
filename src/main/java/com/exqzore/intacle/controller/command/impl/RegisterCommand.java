package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.RequestParameter;
import com.exqzore.intacle.controller.WebPageRequest;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.service.MailService;
import com.exqzore.intacle.model.service.impl.UserServiceImpl;
import com.exqzore.intacle.model.service.status.UserServiceStatus;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class RegisterCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final UserServiceImpl userService = UserServiceImpl.getInstance();

    private static final String IS_MESSAGE_SENT_TO_MAIL = "is_message_sent_to_mail";
    private static final String IS_INVALID_PARAMS = "is_invalid_params";
    private static final String IS_LOGIN_BUSY = "is_login_busy";
    private static final String IS_USER_CREATION_ERROR = "is_user_creation_error";

    @Override
    public String execute(HttpServletRequest request) {
        String resultPage;
        Map<String, String> parameters = new HashMap<>();
        parameters.put(RequestParameter.LOGIN, request.getParameter(RequestParameter.LOGIN));
        parameters.put(RequestParameter.EMAIL, request.getParameter(RequestParameter.EMAIL));
        parameters.put(RequestParameter.PASSWORD, request.getParameter(RequestParameter.PASSWORD));
        parameters.put(RequestParameter.REPEAT_PASSWORD, request.getParameter(RequestParameter.REPEAT_PASSWORD));
        parameters.put(RequestParameter.NAME, request.getParameter(RequestParameter.NAME));
        parameters.put(RequestParameter.SURNAME, request.getParameter(RequestParameter.SURNAME));
        HttpSession session = request.getSession();
        session.setAttribute(IS_INVALID_PARAMS, false);
        session.setAttribute(IS_LOGIN_BUSY, false);
        session.setAttribute(IS_USER_CREATION_ERROR, false);
        session.setAttribute(IS_MESSAGE_SENT_TO_MAIL, false);
        try {
            UserServiceStatus userServiceStatus = userService.register(parameters);
            if (userServiceStatus.isGood()) {
                User user = userServiceStatus.getUser();
                MailService.sendMessage(user.getEmail(), MailService.prepareUrl(user.getLogin(), user.getActivationCode()));
                session.setAttribute(IS_MESSAGE_SENT_TO_MAIL, true);
                resultPage = WebPageRequest.USER_ACTIVATION;
            } else {
                if (userServiceStatus.isInvalidParams()) {
                    session.setAttribute(IS_INVALID_PARAMS, true);
                } else {
                    session.setAttribute(IS_LOGIN_BUSY, true);
                }
                resultPage = WebPageRequest.GO_REGISTRATION_PAGE;
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception.getMessage());
            session.setAttribute(IS_USER_CREATION_ERROR, true);
            resultPage = WebPageRequest.GO_REGISTRATION_PAGE;
        }
        return resultPage;
    }
}
