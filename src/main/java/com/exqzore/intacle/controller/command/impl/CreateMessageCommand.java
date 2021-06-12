package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.Message;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.service.MessageService;
import com.exqzore.intacle.model.service.impl.MessageServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
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
    private static final String AVATAR_PATH = "avatarPath";
    private static final String FORMAT = "dd MMM yyyy HH:mm:ss";

    enum Status {
        SUCCESS, ERROR
    }

    @Override
    public String execute(HttpServletRequest request) {
        String resultJson;
        User user = (User) request.getSession().getAttribute(USER);
        long chatId = Long.parseLong(request.getParameter(CHAT_ID));
        long authorId = user.getId();
        String content = request.getParameter(CONTENT);
        String authorLogin = user.getLogin();
        String authorImagePath = user.getAvatarPath();
        Optional<Message> messageOptional;
        try {
            messageOptional = messageService.create(chatId, content, authorId, authorLogin, authorImagePath);
            if (messageOptional.isPresent()) {
                resultJson = createSuccessJson(messageOptional.get());
            } else {
                resultJson = createErrorJson();
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            resultJson = createErrorJson();
        }
        return resultJson;
    }

    private String createSuccessJson(Message message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(STATUS, Status.SUCCESS);
        jsonObject.put(CONTENT, message.getContent());
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);
        jsonObject.put(CREATION_DATE, formatter.format(message.getCreationDate()));
        jsonObject.put(LOGIN, message.getAuthorLogin());
        jsonObject.put(AVATAR_PATH, message.getAuthorImagePath());
        return jsonObject.toString();
    }

    private String createErrorJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(STATUS, Status.ERROR);
        return jsonObject.toString();
    }
}
