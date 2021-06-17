package com.exqzore.intacle.dao;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.entity.Comment;

import java.util.List;

public interface CommentDao {
    boolean create(Comment comment) throws DaoException;

    boolean update(Comment comment) throws DaoException;

    List<Comment> findByEntryId(long entryId, long userId) throws DaoException;

    boolean like(long commentId, long userId) throws DaoException;

    boolean unlike(long commentId, long userId) throws DaoException;

    boolean removeById(long commentId) throws DaoException;
}
