package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.ErrorPageAttribute;
import com.exqzore.intacle.controller.WebPagePath;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.entity.Chat;
import com.exqzore.intacle.entity.Message;
import com.exqzore.intacle.entity.User;
import com.exqzore.intacle.entity.UserRole;
import com.exqzore.intacle.service.ChatService;
import com.exqzore.intacle.service.MessageService;
import com.exqzore.intacle.service.impl.ChatServiceImpl;
import com.exqzore.intacle.service.impl.MessageServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class CreateChatCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final MessageService messageService = MessageServiceImpl.getInstance();
    private final ChatService chatService = ChatServiceImpl.getInstance();

    private static final String USER = "user";
    private static final String MESSAGES = "messages";
    private static final String CHAT_ID = "chat";
    private static final String USER_ID = "user_id";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String pagePath;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        long userId = Long.parseLong(request.getParameter(USER_ID));
        try {
            Optional<Chat> chatOptional = chatService.create(user.getId(), userId);
            if (chatOptional.isPresent()) {
                Chat chat = chatOptional.get();
                messageService.defineOld(chat.getId(), user.getId());
                List<Message> messages = messageService.findAllByChatId(chat.getId());
                session.setAttribute(MESSAGES, messages);
                session.setAttribute(CHAT_ID, chat.getId());
                pagePath = WebPagePath.CHAT;
            } else {
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                pagePath = WebPagePath.ERROR_PAGE;
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            request.setAttribute(ErrorPageAttribute.EXCEPTION, exception);
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            pagePath = WebPagePath.ERROR_PAGE;
        }
        return pagePath;
    }

    @Override
    public List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.USER, UserRole.EDITOR, UserRole.ADMIN);
    }
}
