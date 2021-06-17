package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.WebPageRequest;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.exception.InvalidParamsException;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.exception.UserLoginIsBusyException;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.service.MailService;
import com.exqzore.intacle.model.service.UserService;
import com.exqzore.intacle.model.service.impl.MailServiceImpl;
import com.exqzore.intacle.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class RegisterCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final UserService userService = UserServiceImpl.getInstance();
    private final MailService mailService = MailServiceImpl.getInstance();

    private static final String LOGIN = "login";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String REPEAT_PASSWORD = "repeat_password";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String IS_MESSAGE_SENT_TO_MAIL = "isMessageSentToMail";
    private static final String IS_INVALID_PARAMS = "isInvalidParams";
    private static final String IS_LOGIN_BUSY = "isLoginBusy";
    private static final String IS_USER_CREATION_ERROR = "isUserCreationError";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String resultPage;
        String login = request.getParameter(LOGIN);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String repeatPassword = request.getParameter(REPEAT_PASSWORD);
        String name = request.getParameter(NAME);
        String surname = request.getParameter(SURNAME);
        HttpSession session = request.getSession();
        session.setAttribute(IS_INVALID_PARAMS, false);
        session.setAttribute(IS_LOGIN_BUSY, false);
        session.setAttribute(IS_USER_CREATION_ERROR, false);
        session.setAttribute(IS_MESSAGE_SENT_TO_MAIL, false);
        try {
            Optional<User> userOptional = userService.register(login, email, password, repeatPassword, name, surname);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                mailService.sendMessage(user.getEmail(), mailService.prepareUrl(user.getLogin(), user.getActivationCode()));
                session.setAttribute(IS_MESSAGE_SENT_TO_MAIL, true);
                resultPage = WebPageRequest.USER_ACTIVATION;
            } else {
                session.setAttribute(IS_USER_CREATION_ERROR, true);
                resultPage = WebPageRequest.GO_REGISTRATION_PAGE;
            }
        } catch (UserLoginIsBusyException exception) {
            logger.log(Level.INFO, exception);
            session.setAttribute(IS_LOGIN_BUSY, true);
            resultPage = WebPageRequest.GO_REGISTRATION_PAGE;
        } catch (InvalidParamsException exception) {
            logger.log(Level.INFO, exception);
            session.setAttribute(IS_INVALID_PARAMS, true);
            resultPage = WebPageRequest.GO_REGISTRATION_PAGE;
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            session.setAttribute(IS_USER_CREATION_ERROR, true);
            resultPage = WebPageRequest.GO_REGISTRATION_PAGE;
        }
        return resultPage;
    }
}
