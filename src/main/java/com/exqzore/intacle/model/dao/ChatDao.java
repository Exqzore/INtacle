package com.exqzore.intacle.model.dao;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.model.entity.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatDao {
    boolean create(long firstUserId, long secondUserId) throws DaoException;

    Optional<Chat> findByUsers(long firstUserId, long secondUserId) throws DaoException;

    List<Chat> findUserChat(long userId) throws DaoException;
}
