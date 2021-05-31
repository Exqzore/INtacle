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

public class UnsubscribeCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final SubscriberService subscriberService = SubscriberServiceImpl.getInstance();

    private static final String LOGIN = "login";
    private static final String USER = "user";

    @Override
    public String execute(HttpServletRequest request) { //TODO: add normal error page
        String pagePath;
        String subscriptionLogin = request.getParameter(LOGIN);
        User user = (User) request.getSession().getAttribute(USER);
        if (user != null) {
            String subscriberLogin = user.getLogin();
            try {
                if (subscriberService.unsubscribe(subscriberLogin, subscriptionLogin)) {
                    pagePath = WebPagePath.EMPTY_PAGE; //TODO: something wrong
                } else {
                    pagePath =WebPagePath.ERROR_PAGE;
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
