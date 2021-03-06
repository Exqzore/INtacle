package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.WebPageRequest;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.entity.UserRole;
import com.exqzore.intacle.exception.InvalidParamsException;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.exception.UserNotActivatedException;
import com.exqzore.intacle.entity.User;
import com.exqzore.intacle.service.UserService;
import com.exqzore.intacle.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final UserService userService = UserServiceImpl.getInstance();

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String IS_INVALID_PARAMS = "isInvalidParams";
    private static final String IS_USER_LOGIN_ERROR = "isUserLoginError";
    private static final String IS_NOT_ACTIVATED = "isNotActivated";
    private static final String USER = "user";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String resultPage;
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        HttpSession session = request.getSession();
        session.setAttribute(IS_INVALID_PARAMS, false);
        session.setAttribute(IS_USER_LOGIN_ERROR, false);
        session.setAttribute(IS_NOT_ACTIVATED, false);
        try {
            Optional<User> userOptional = userService.login(login, password);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                session.setAttribute(USER, user);
                resultPage = String.format(WebPageRequest.REDIRECT + WebPageRequest.SHOW_PROFILE, user.getLogin());
            } else {
                session.setAttribute(IS_INVALID_PARAMS, true);
                resultPage = WebPageRequest.GO_LOGIN_PAGE;
            }
        } catch (UserNotActivatedException exception) {
            logger.log(Level.ERROR, exception);
            session.setAttribute(IS_NOT_ACTIVATED, true);
            resultPage = WebPageRequest.GO_LOGIN_PAGE;
        } catch (InvalidParamsException exception) {
            logger.log(Level.ERROR, exception);
            session.setAttribute(IS_INVALID_PARAMS, true);
            resultPage = WebPageRequest.GO_LOGIN_PAGE;
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            session.setAttribute(IS_USER_LOGIN_ERROR, true);
            resultPage = WebPageRequest.GO_LOGIN_PAGE;
        }
        return resultPage;
    }

    @Override
    public List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.values());
    }
}
