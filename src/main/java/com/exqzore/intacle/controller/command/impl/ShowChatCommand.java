package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.WebPagePath;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.Message;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.service.MessageService;
import com.exqzore.intacle.model.service.impl.MessageServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowChatCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final MessageService messageService = MessageServiceImpl.getInstance();

    private static final String USER = "user";
    private static final String MESSAGES = "messages";
    private static final String CHAT_ID = "chat";

    @Override
    public String execute(HttpServletRequest request) { //TODO: add normal error page
        String pagePath;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        long chatId = Long.parseLong(request.getParameter(CHAT_ID));
        try {
            if (messageService.defineOld(chatId, user.getId())) {
                List<Message> messages = messageService.allByChatId(chatId);
                session.setAttribute(MESSAGES, messages);
                session.setAttribute(CHAT_ID, chatId);
                pagePath = WebPagePath.CHAT;
            } else {
                pagePath = WebPagePath.ERROR_PAGE;
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            pagePath = WebPagePath.ERROR_PAGE;
        }
        return pagePath;
    }
}
