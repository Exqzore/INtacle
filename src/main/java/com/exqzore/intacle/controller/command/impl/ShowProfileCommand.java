package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.ErrorPageAttribute;
import com.exqzore.intacle.controller.WebPagePath;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.Entry;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.entity.UserRole;
import com.exqzore.intacle.model.service.EntryService;
import com.exqzore.intacle.model.service.SubscriberService;
import com.exqzore.intacle.model.service.UserService;
import com.exqzore.intacle.model.service.impl.EntryServiceImpl;
import com.exqzore.intacle.model.service.impl.SubscriberServiceImpl;
import com.exqzore.intacle.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShowProfileCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final UserService userService = UserServiceImpl.getInstance();
    private final EntryService entryService = EntryServiceImpl.getInstance();
    private final SubscriberService subscriberService = SubscriberServiceImpl.getInstance();

    private static final String REQUEST_LOGIN = "login";
    private static final String REQUESTED_USER = "requestedUser";
    private static final String IS_SUBSCRIBE = "isSubscribe";
    private static final String USER = "user";
    private static final String CAN_EDIT = "canEdit";
    private static final String ENTRIES = "entries";
    private static final String CAN_EDIT_ENTRIES = "canEditEntries";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String resultPage;
        HttpSession session = request.getSession();
        String login = request.getParameter(REQUEST_LOGIN);
        User user = (User) request.getSession().getAttribute(USER);
        boolean canEdit = login.equals(user.getLogin());
        try {
            Optional<User> userOptional = userService.findByLogin(login);
            if (userOptional.isPresent()) {
                User requestUser = userOptional.get();
                if (canEdit) {
                    session.setAttribute(USER, requestUser);
                } else {
                    session.setAttribute(IS_SUBSCRIBE, subscriberService.isSubscribe(user.getId(), requestUser.getId()));
                }
                if (user.getRole().equals(UserRole.ADMIN) || user.getRole().equals(UserRole.EDITOR) || canEdit) {
                    session.setAttribute(CAN_EDIT_ENTRIES, true);
                } else {
                    session.setAttribute(CAN_EDIT_ENTRIES, false);
                }
                session.setAttribute(CAN_EDIT, canEdit);
                session.setAttribute(REQUESTED_USER, requestUser);
                List<Entry> entries = entryService.findByUserLogin(login, user.getId());
                session.setAttribute(ENTRIES, entries);
                resultPage = WebPagePath.PROFILE_PAGE;
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resultPage = WebPagePath.ERROR_PAGE;
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            request.setAttribute(ErrorPageAttribute.EXCEPTION, exception);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resultPage = WebPagePath.ERROR_PAGE;
        }
        return resultPage;
    }

    @Override
    public List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.USER, UserRole.EDITOR, UserRole.ADMIN);
    }
}
