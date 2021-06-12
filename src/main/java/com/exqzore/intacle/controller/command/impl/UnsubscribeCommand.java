package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.service.SubscriberService;
import com.exqzore.intacle.model.service.impl.SubscriberServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UnsubscribeCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final SubscriberService subscriberService = SubscriberServiceImpl.getInstance();

    private static final String LOGIN = "login";
    private static final String USER = "user";
    private static final String STATUS = "status";

    enum Status {
        SUCCESS, ERROR
    }

    @Override
    public String execute(HttpServletRequest request) { //TODO: add normal error page
        String resultJson;
        String subscriptionLogin = request.getParameter(LOGIN);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        String subscriberLogin = user.getLogin();
        try {
            if (subscriberService.unsubscribe(subscriberLogin, subscriptionLogin)) {
                resultJson = createJson(Status.SUCCESS);
//                    pagePath = WebPagePath.EMPTY_PAGE; //TODO: something wrong
            } else {
                resultJson = createJson(Status.ERROR);
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            resultJson = createJson(Status.ERROR);
        }
        return resultJson;
    }

    private String createJson(Status status) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(STATUS, status);
        return jsonObject.toString();
    }
}
