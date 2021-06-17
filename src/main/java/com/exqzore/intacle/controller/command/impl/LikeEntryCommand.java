package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.ErrorPageAttribute;
import com.exqzore.intacle.controller.JsonStatus;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.entity.UserRole;
import com.exqzore.intacle.model.service.EntryService;
import com.exqzore.intacle.model.service.impl.EntryServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LikeEntryCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final EntryService entryService = EntryServiceImpl.getInstance();

    private static final String ENTRY_ID = "entry";
    private static final String USER = "user";
    private static final String STATUS = "status";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String resultJson;
        long entryId = Long.parseLong(request.getParameter(ENTRY_ID));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        try {
            entryService.like(entryId, user.getId());
            resultJson = createJson(JsonStatus.SUCCESS);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            request.setAttribute(ErrorPageAttribute.EXCEPTION, exception);
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            resultJson = createJson(JsonStatus.ERROR);
        }
        return resultJson;
    }

    @Override
    public List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.USER, UserRole.EDITOR, UserRole.ADMIN);
    }

    private String createJson(JsonStatus status) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(STATUS, status);
        return jsonObject.toString();
    }
}
