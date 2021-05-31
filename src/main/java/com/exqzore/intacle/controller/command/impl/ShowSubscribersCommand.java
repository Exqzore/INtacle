package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.WebPagePath;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.service.SubscriberService;
import com.exqzore.intacle.model.service.impl.SubscriberServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowSubscribersCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final SubscriberService subscriberService = SubscriberServiceImpl.getInstance();

    private static final String LOGIN = "login";
    private static final String USERS = "users";
    private static final int COUNT = 20;
    private static final int OFFSET = 0; //TODO: add normal offset

    @Override
    public String execute(HttpServletRequest request) { //TODO: add normal error page
        String pagePath;
        HttpSession session = request.getSession();
        String login = request.getParameter(LOGIN);
        try {
            List<User> users = subscriberService.findUserSubscribers(login, COUNT, OFFSET);
            session.setAttribute(USERS, users);
            pagePath = WebPagePath.SUBSCRIBERS_PAGE;
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            pagePath = WebPagePath.ERROR_PAGE;
        }
        return pagePath;
    }
}
