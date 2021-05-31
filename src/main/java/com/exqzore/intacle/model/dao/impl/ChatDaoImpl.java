package com.exqzore.intacle.model.dao.impl;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.model.dao.ChatDao;
import com.exqzore.intacle.model.entity.Chat;

import java.util.List;

public class ChatDaoImpl implements ChatDao {
    @Override
    public List<Chat> findUserChat(long userId) throws DaoException {
        return null;
    }
}
