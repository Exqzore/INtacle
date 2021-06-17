package com.exqzore.intacle.dao;

import com.exqzore.intacle.entity.Chat;
import com.exqzore.intacle.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface ChatDao {
    Optional<Chat> create(long firstUserId, long secondUserId) throws DaoException;

    Optional<Chat> findByUsersId(long firstUserId, long secondUserId) throws DaoException;

    List<Chat> findByUserId(long userId) throws DaoException;
}
