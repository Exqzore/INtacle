package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.ErrorPageAttribute;
import com.exqzore.intacle.controller.WebPagePath;
import com.exqzore.intacle.controller.WebPageRequest;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.entity.User;
import com.exqzore.intacle.entity.UserRole;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.service.CommentService;
import com.exqzore.intacle.service.impl.CommentServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CreateCommentCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final CommentService commentService = CommentServiceImpl.getInstance();

    private static final String USER = "user";
    private static final String ENTRY_ID = "entry";
    private static final String CONTENT = "content";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String resultPage;
        User user = (User) request.getSession().getAttribute(USER);
        long entryId = Long.parseLong(request.getParameter(ENTRY_ID));
        String content = request.getParameter(CONTENT);
        try {
            if (commentService.create(entryId, content, user)) {
                resultPage = String.format(WebPageRequest.REDIRECT + WebPageRequest.SHOW_ENTRY, entryId);
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
