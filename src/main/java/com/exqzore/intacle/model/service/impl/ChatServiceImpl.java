package com.exqzore.intacle.model.service.impl;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.dao.ChatDao;
import com.exqzore.intacle.model.dao.impl.ChatDaoImpl;
import com.exqzore.intacle.model.entity.Chat;
import com.exqzore.intacle.model.service.ChatService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ChatServiceImpl implements ChatService {
    private final static Logger logger = LogManager.getLogger();

    private static final ChatService instance = new ChatServiceImpl();

    private final ChatDao chatDao = ChatDaoImpl.getInstance();

    private ChatServiceImpl() {
    }

    public static ChatService getInstance() {
        return instance;
    }

    @Override
    public boolean create(long firstUserId, long secondUserId) throws ServiceException {
        return false;
    }

    @Override
    public List<Chat> userChats(long userId) throws ServiceException {
        List<Chat> chats;
        try {
            chats = chatDao.findUserChat(userId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return chats;
    }
}
