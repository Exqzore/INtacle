package com.exqzore.intacle.dao;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.entity.Entry;

import java.util.List;
import java.util.Optional;

public interface EntryDao {
    List<Entry> findByUserLogin(String login, long userId) throws DaoException;

    List<Entry> findByUserSubscription(long userId) throws DaoException;

    Optional<Entry> findById(long entryId, long userId) throws DaoException;

    Optional<Entry> create(Entry entry) throws DaoException;

    boolean update(Entry entry) throws DaoException;

    boolean removeById(long entryId) throws DaoException;

    boolean like(long entryId, long userId) throws DaoException;

    boolean unlike(long entryId, long userId) throws DaoException;
}
