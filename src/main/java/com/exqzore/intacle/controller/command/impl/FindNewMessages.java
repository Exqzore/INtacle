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
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;

public class FindNewMessages implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final MessageService messageService = MessageServiceImpl.getInstance();

    private static final String USER = "user";
    private static final String CHAT_ID = "chat";

    private static final String STATUS = "status";
    private static final String MESSAGES = "messages";
    private static final String CONTENT = "content";
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
        long senderId = user.getId();
        try {
            List<Message> messages = messageService.newSenderByChatId(chatId, senderId);
            if (messageService.defineOld(chatId, user.getId())) {
                resultJson = createSuccessJson(messages);
            } else {
                resultJson = createErrorJson();
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            resultJson = createErrorJson();
        }
        return resultJson;
    }

    private String createSuccessJson(List<Message> messages) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put(STATUS, Status.SUCCESS);
        for (Message message : messages) {
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put(CONTENT, message.getContent());
            SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);
            jsonMessage.put(CREATION_DATE, formatter.format(message.getCreationDate()));
            jsonMessage.put(LOGIN, message.getAuthorLogin());
            jsonMessage.put(AVATAR_PATH, message.getAuthorImagePath());
            jsonArray.put(jsonMessage);
        }
        jsonObject.put(MESSAGES, jsonArray);
        return jsonObject.toString();
    }

    private String createErrorJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(STATUS, Status.ERROR);
        return jsonObject.toString();
    }
}
