package com.exqzore.intacle.model.dao;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.model.entity.Entry;

import java.util.List;

public interface EntryDao {
    List<Entry> findAllUserSubscriptions(long userId) throws DaoException;
}
