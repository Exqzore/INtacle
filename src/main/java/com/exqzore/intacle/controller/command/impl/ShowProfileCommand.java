package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.WebPagePath;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.service.UserService;
import com.exqzore.intacle.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ShowProfileCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final UserService userService = UserServiceImpl.getInstance();

    private static final String REQUEST_LOGIN = "login";
    private static final String CAN_EDIT = "canEdit";

    private static final String REQUESTED_USER = "requestedUser";
    private static final String IS_SUBSCRIBE = "isSubscribe";
    private static final String USER = "user";

    @Override
    public String execute(HttpServletRequest request) {
        String pagePath;
        HttpSession session = request.getSession();
        logger.log(Level.INFO, "We are here1");
        if (true) {
            String login = request.getParameter(REQUEST_LOGIN);
            User user = (User) request.getSession().getAttribute(USER);
            boolean canEdit = login.equals(user.getLogin());

            if (canEdit) {
                session.setAttribute(CAN_EDIT, true);
                session.setAttribute(REQUESTED_USER, user);
                pagePath = WebPagePath.PROFILE_PAGE;
            } else {
                try {
                    Optional<User> userOptional = userService.userByLogin(login);
                    if (userOptional.isPresent()) {
                        User requestUser = userOptional.get();
                        session.setAttribute(CAN_EDIT, false);
                        session.setAttribute(REQUESTED_USER, requestUser);
                        session.setAttribute(IS_SUBSCRIBE, userService.isSubscribe(user.getId(), requestUser.getId()));
                        pagePath = WebPagePath.PROFILE_PAGE;
                    } else {
                        pagePath = WebPagePath.ERROR_PAGE;
                    }
                } catch (ServiceException exception) {
                    pagePath = WebPagePath.ERROR_PAGE;
                }
            }
        } else {
            pagePath = WebPagePath.LOGIN_PAGE;
        }
        return pagePath;
    }
}
