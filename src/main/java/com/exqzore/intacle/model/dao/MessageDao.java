package com.exqzore.intacle.model.dao;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.model.entity.Message;

import java.util.List;

public interface MessageDao {
    boolean create(Message message) throws DaoException;

    boolean update(Message message) throws DaoException;

    boolean defineOdl(long chatId, long senderId) throws DaoException;

    List<Message> findByChatId(long chatId) throws DaoException;

    List<Message> findNewSenderByChatId(long chatId, long senderId) throws DaoException;
}
