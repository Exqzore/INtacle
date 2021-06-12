package com.exqzore.intacle.model.service;

import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    List<Message> allByChatId(long chatId) throws ServiceException;

    List<Message> newSenderByChatId(long chatId, long senderId) throws ServiceException;

    boolean defineOld(long chatId, long senderId) throws ServiceException;

    Optional<Message> create(long chatId, String content, long authorId, String authorLogin, String authorImagePath) throws ServiceException;
}
