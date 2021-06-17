package com.exqzore.intacle.service;

import com.exqzore.intacle.entity.Chat;
import com.exqzore.intacle.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface ChatService {
    Optional<Chat> create(long firstUserId, long secondUserId) throws ServiceException;

    List<Chat> findChatsByUserId(long userId) throws ServiceException;
}
