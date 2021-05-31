package com.exqzore.intacle.model.dao;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.model.entity.Chat;

import java.util.List;

public interface ChatDao {
    List<Chat> findUserChat(long userId) throws DaoException;
}
