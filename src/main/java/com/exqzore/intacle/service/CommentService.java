package com.exqzore.intacle.service;

import com.exqzore.intacle.entity.Comment;
import com.exqzore.intacle.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<Comment> findByEntryId(long entryId, long userId) throws ServiceException;

    boolean removeById(long commentId) throws ServiceException;

    boolean like(long commentId, long userId) throws ServiceException;

    boolean unlike(long commentId, long userId) throws ServiceException;

    Optional<Comment> create(long entryId, String content, long authorId, String authorLogin,
                             String authorAvatarImagePath) throws ServiceException;
}
