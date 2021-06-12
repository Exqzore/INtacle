package com.exqzore.intacle.model.service.impl;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.exception.InvalidParamsException;
import com.exqzore.intacle.exception.PasswordEncoderException;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.dao.MessageDao;
import com.exqzore.intacle.model.dao.impl.MessageDaoImpl;
import com.exqzore.intacle.model.entity.Message;
import com.exqzore.intacle.model.service.MessageService;
import com.exqzore.intacle.model.validator.LoginValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MessageServiceImpl implements MessageService {
    private final static Logger logger = LogManager.getLogger();

    private static final MessageService instance = new MessageServiceImpl();

    private final MessageDao messageDao = MessageDaoImpl.getInstance();

    private MessageServiceImpl() {
    }

    public static MessageService getInstance() {
        return instance;
    }

    @Override
    public List<Message> allByChatId(long chatId) throws ServiceException {
        List<Message> messages;
        try {
            messages = messageDao.findByChatId(chatId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return messages;
    }

    @Override
    public List<Message> newSenderByChatId(long chatId, long senderId) throws ServiceException {
        List<Message> messages;
        try {
            messages = messageDao.findNewSenderByChatId(chatId, senderId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return messages;
    }

    @Override
    public boolean defineOld(long chatId, long senderId) throws ServiceException {
        boolean result;
        try {
            result = messageDao.defineOdl(chatId, senderId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return result;
    }

    @Override
    public Optional<Message> create(long chatId, String content, long authorId, String authorLogin, String authorImagePath) throws ServiceException {
        Optional<Message> messageOptional;
        if (LoginValidator.checkLogin(authorLogin)) {
            try {
                Message message = new Message();
                message.setChatId(chatId);
                message.setContent(content);
                message.setAuthorId(authorId);
                message.setCreationDate(new Date());
                message.setUpdateDate(message.getCreationDate());
                message.setAuthorLogin(authorLogin);
                message.setAuthorImagePath(authorImagePath);
                if (messageDao.create(message)) {
                    messageOptional = Optional.of(message);
                } else {
                    messageOptional = Optional.empty();
                }
            } catch (DaoException exception) {
                logger.log(Level.ERROR, exception);
                throw new ServiceException(exception);
            }
        } else {
            throw new InvalidParamsException();
        }
        return messageOptional;
    }
}
