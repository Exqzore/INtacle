package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.ErrorPageAttribute;
import com.exqzore.intacle.controller.JsonStatus;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.Message;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.entity.UserRole;
import com.exqzore.intacle.model.service.MessageService;
import com.exqzore.intacle.model.service.impl.MessageServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

public class CreateMessageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final MessageService messageService = MessageServiceImpl.getInstance();

    private static final String USER = "user";
    private static final String CHAT_ID = "chat";
    private static final String CONTENT = "content";

    private static final String STATUS = "status";
    private static final String CREATION_DATE = "creationDate";
    private static final String LOGIN = "login";
    private static final String AVATAR_IMAGE_PATH = "avatarImagePath";
    private static final String FORMAT = "dd MMM yyyy HH:mm:ss";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String resultJson;
        User user = (User) request.getSession().getAttribute(USER);
        long chatId = Long.parseLong(request.getParameter(CHAT_ID));
        long authorId = user.getId();
        String authorLogin = user.getLogin();
        String authorAvatarImagePath = user.getAvatarImagePath();
        String content = request.getParameter(CONTENT);
        Optional<Message> messageOptional;
        try {
            messageOptional = messageService.create(chatId, content, authorId, authorLogin, authorAvatarImagePath);
            if (messageOptional.isPresent()) {
                resultJson = createSuccessJson(messageOptional.get());
            } else {
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                resultJson = createErrorJson();
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            request.setAttribute(ErrorPageAttribute.EXCEPTION, exception);
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            resultJson = createErrorJson();
        }
        return resultJson;
    }

    @Override
    public List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.USER, UserRole.EDITOR, UserRole.ADMIN);
    }

    private String createSuccessJson(Message message) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(STATUS, JsonStatus.SUCCESS);
        jsonObject.put(CONTENT, message.getContent());
        jsonObject.put(CREATION_DATE, formatter.format(message.getCreationDate()));
        jsonObject.put(LOGIN, message.getAuthor().getLogin());
        jsonObject.put(AVATAR_IMAGE_PATH, message.getAuthor().getAvatarImagePath());
        return jsonObject.toString();
    }

    private String createErrorJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(STATUS, JsonStatus.ERROR);
        return jsonObject.toString();
    }
}
