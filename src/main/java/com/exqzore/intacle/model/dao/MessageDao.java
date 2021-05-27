package com.exqzore.intacle.model.dao;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.model.entity.Message;

import java.util.List;

public interface MessageDao {
    void create(Message message) throws DaoException;

    void update(Message message) throws DaoException;

    List<Message> findByChatId(long chatId) throws DaoException;

    List<Message> findNewByChatId(long chatId) throws DaoException;
}
