package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.WebPagePath;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.service.SubscriberService;
import com.exqzore.intacle.model.service.UserService;
import com.exqzore.intacle.model.service.impl.SubscriberServiceImpl;
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
    private final SubscriberService subscriberService = SubscriberServiceImpl.getInstance();

    private static final String REQUEST_LOGIN = "login";
    private static final String REQUESTED_USER = "requestedUser";
    private static final String IS_SUBSCRIBE = "isSubscribe";
    private static final String USER = "user";
    private static final String CAN_EDIT = "canEdit";

    @Override
    public String execute(HttpServletRequest request) { //TODO: add normal error page
        String pagePath;
        HttpSession session = request.getSession();
        String login = request.getParameter(REQUEST_LOGIN);
        User user = (User) request.getSession().getAttribute(USER);
        if (user != null) {
            boolean canEdit = login.equals(user.getLogin());
            try {
                if (canEdit) {
                    session.setAttribute(CAN_EDIT, true);
                    session.setAttribute(REQUESTED_USER, user);
                    pagePath = WebPagePath.PROFILE_PAGE;
                } else {
                    Optional<User> userOptional = userService.findByLogin(login);
                    if (userOptional.isPresent()) {
                        User requestUser = userOptional.get();
                        session.setAttribute(CAN_EDIT, false);
                        session.setAttribute(REQUESTED_USER, requestUser);
                        session.setAttribute(IS_SUBSCRIBE, subscriberService.isSubscribe(user.getId(), requestUser.getId()));
                        pagePath = WebPagePath.PROFILE_PAGE;
                    } else {
                        pagePath = WebPagePath.ERROR_PAGE;
                    }
                }
            } catch (ServiceException exception) {
                logger.log(Level.ERROR, exception);
                pagePath = WebPagePath.ERROR_PAGE;
            }
        } else {
            pagePath = WebPagePath.ERROR_PAGE;
        }
        return pagePath;
    }
}
