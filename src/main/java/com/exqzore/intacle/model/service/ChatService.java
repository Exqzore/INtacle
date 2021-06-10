package com.exqzore.intacle.model.service;

import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.Chat;

import java.util.List;

public interface ChatService {
    boolean create(long firstUserId, long secondUserId) throws ServiceException;

    List<Chat> userChats(long userId) throws ServiceException;
}
