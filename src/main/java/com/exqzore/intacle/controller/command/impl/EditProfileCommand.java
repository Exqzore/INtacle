package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.ErrorPageAttribute;
import com.exqzore.intacle.controller.WebPagePath;
import com.exqzore.intacle.controller.WebPageRequest;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.entity.UserRole;
import com.exqzore.intacle.model.service.UserService;
import com.exqzore.intacle.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class EditProfileCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final UserService userService = UserServiceImpl.getInstance();

    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String USER = "user";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String resultPage;
        User user = (User) request.getSession().getAttribute(USER);
        String name = request.getParameter(NAME);
        name = name.isEmpty() ? null : name;
        String surname = request.getParameter(SURNAME);
        surname = surname.isEmpty() ? null : surname;
        HttpSession session = request.getSession();
        try {
            if (userService.edit(user.getLogin(), name, surname)) {
                user.setName(name);
                user.setSurname(surname);
                session.setAttribute(USER, user);
                resultPage = String.format(WebPageRequest.SHOW_PROFILE, user.getLogin());
            } else {
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                resultPage = WebPagePath.ERROR_PAGE;
            }
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
