package com.exqzore.intacle.dao;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.entity.Message;

import java.util.List;

public interface MessageDao {
    boolean create(Message message) throws DaoException;

    boolean update(Message message) throws DaoException;

    void defineOdl(long chatId, long senderId) throws DaoException;

    List<Message> findByChatId(long chatId) throws DaoException;

    List<Message> findNewNotSenderByChatId(long chatId, long senderId) throws DaoException;
}
