package com.exqzore.intacle.model.service;

import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatService {
    Optional<Chat> create(long firstUserId, long secondUserId) throws ServiceException;

    List<Chat> findChatsByUserId(long userId) throws ServiceException;
}
