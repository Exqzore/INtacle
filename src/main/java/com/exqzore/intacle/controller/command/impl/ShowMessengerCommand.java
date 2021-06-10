package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.WebPagePath;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.Chat;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.service.ChatService;
import com.exqzore.intacle.model.service.impl.ChatServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowMessengerCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final ChatService chatService = ChatServiceImpl.getInstance();

    private static final String CHATS = "chats";
    private static final String USER = "user";

    @Override
    public String execute(HttpServletRequest request) { //TODO: add normal error page
        String pagePath;
        HttpSession session = request.getSession();
        User user = (User) request.getSession().getAttribute(USER);
        try {
            List<Chat> chats = chatService.userChats(user.getId());
            session.setAttribute(CHATS, chats);
            pagePath = WebPagePath.MESSENGER;
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            pagePath = WebPagePath.ERROR_PAGE;
        }
        return pagePath;
    }
}
