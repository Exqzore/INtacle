package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.ErrorPageAttribute;
import com.exqzore.intacle.controller.WebPagePath;
import com.exqzore.intacle.controller.WebPageRequest;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.entity.UserRole;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.service.EntryService;
import com.exqzore.intacle.service.impl.EntryServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EditEntryCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final EntryService entryService = EntryServiceImpl.getInstance();

    private static final String ENTRY_ID = "entry";
    private static final String TITLE = "title";
    private static final String SUMMARY = "summary";
    private static final String CONTENT = "content";
    private static final String PREVIEW_IMAGE_PATH = "imagePath";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String resultPage;
        long entryId = Long.parseLong(request.getParameter(ENTRY_ID));
        String title = request.getParameter(TITLE);
        String summary = request.getParameter(SUMMARY);
        String content = request.getParameter(CONTENT);
        String previewImagePath = request.getParameter(PREVIEW_IMAGE_PATH);
        try {
            if (entryService.edit(entryId, title, summary, content, previewImagePath)) {
                resultPage = String.format(WebPageRequest.SHOW_ENTRY, entryId);
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
