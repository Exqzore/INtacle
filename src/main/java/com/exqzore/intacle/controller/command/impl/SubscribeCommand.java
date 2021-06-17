package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.ErrorPageAttribute;
import com.exqzore.intacle.controller.WebPagePath;
import com.exqzore.intacle.controller.WebPageRequest;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.entity.UserRole;
import com.exqzore.intacle.model.service.SubscriberService;
import com.exqzore.intacle.model.service.impl.SubscriberServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SubscribeCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final SubscriberService subscriberService = SubscriberServiceImpl.getInstance();

    private static final String LOGIN = "login";
    private static final String USER = "user";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String resultPage;
        String subscriptionLogin = request.getParameter(LOGIN);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        try {
            subscriberService.subscribe(user.getLogin(), subscriptionLogin);
            resultPage = String.format(WebPageRequest.SHOW_PROFILE, subscriptionLogin);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            request.setAttribute(ErrorPageAttribute.EXCEPTION, exception);
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            resultPage = WebPagePath.ERROR_PAGE;
        }
        return resultPage;
    }

    @Override
    public List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.USER, UserRole.EDITOR, UserRole.ADMIN);
    }
}
