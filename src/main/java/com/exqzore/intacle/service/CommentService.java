package com.exqzore.intacle.service;

import com.exqzore.intacle.entity.Comment;
import com.exqzore.intacle.entity.User;
import com.exqzore.intacle.exception.ServiceException;

import java.util.List;

public interface CommentService {
    List<Comment> findByEntryId(long entryId, long userId) throws ServiceException;

    boolean removeById(long commentId) throws ServiceException;

    boolean like(long commentId, long userId) throws ServiceException;

    boolean unlike(long commentId, long userId) throws ServiceException;

    boolean create(long entryId, String content, User author) throws ServiceException;
}
