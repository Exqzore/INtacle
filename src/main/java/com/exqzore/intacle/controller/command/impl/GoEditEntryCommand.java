package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.ErrorPageAttribute;
import com.exqzore.intacle.controller.WebPagePath;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.entity.Comment;
import com.exqzore.intacle.entity.Entry;
import com.exqzore.intacle.entity.User;
import com.exqzore.intacle.entity.UserRole;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.service.CommentService;
import com.exqzore.intacle.service.EntryService;
import com.exqzore.intacle.service.impl.CommentServiceImpl;
import com.exqzore.intacle.service.impl.EntryServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class GoEditEntryCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final EntryService entryService = EntryServiceImpl.getInstance();

    private static final String USER = "user";
    private static final String ENTRY_ID = "entry";
    private static final String ENTRY = "entry";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String resultPage;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        long entryId = Long.parseLong(request.getParameter(ENTRY_ID));
        try {
            Optional<Entry> entryOptional = entryService.findById(entryId, user.getId());
            if (entryOptional.isPresent()) {
                Entry entry = entryOptional.get();
                boolean canEdit = entry.getAuthor().getId() == user.getId()
                        || List.of(UserRole.EDITOR, UserRole.ADMIN).contains(user.getRole());
                if(canEdit) {
                    session.setAttribute(ENTRY, entry);
                    resultPage = WebPagePath.EDIT_ENTRY_PAGE;
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    resultPage = WebPagePath.ERROR_PAGE;
                }
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
