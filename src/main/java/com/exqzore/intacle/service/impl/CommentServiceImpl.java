package com.exqzore.intacle.service.impl;

import com.exqzore.intacle.dao.CommentDao;
import com.exqzore.intacle.dao.impl.CommentDaoImpl;
import com.exqzore.intacle.entity.Comment;
import com.exqzore.intacle.entity.Entry;
import com.exqzore.intacle.entity.User;
import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.service.CommentService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;

public class CommentServiceImpl implements CommentService {
    private final static Logger logger = LogManager.getLogger();

    private static final CommentService instance = new CommentServiceImpl();

    private final CommentDao commentDao = CommentDaoImpl.getInstance();

    private CommentServiceImpl() {
    }

    public static CommentService getInstance() {
        return instance;
    }

    @Override
    public List<Comment> findByEntryId(long entryId, long userId) throws ServiceException {
        List<Comment> comments;
        try {
            comments = commentDao.findByEntryId(entryId, userId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return comments;
    }

    @Override
    public boolean removeById(long commentId) throws ServiceException {
        boolean isDeleted;
        try {
            isDeleted = commentDao.removeById(commentId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return isDeleted;
    }

    @Override
    public boolean like(long commentId, long userId) throws ServiceException {
        boolean isLiked;
        try {
            isLiked = commentDao.like(commentId, userId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return isLiked;
    }

    @Override
    public boolean unlike(long commentId, long userId) throws ServiceException {
        boolean isUnliked;
        try {
            isUnliked = commentDao.unlike(commentId, userId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return isUnliked;
    }

    @Override
    public boolean create(long entryId, String content, User author) throws ServiceException {
        boolean result;
        try {
            Entry entry = new Entry();
            entry.setId(entryId);
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setCreationDate(new Date());
            comment.setUpdateDate(comment.getCreationDate());
            comment.setEntry(entry);
            comment.setAuthor(author);
            result = commentDao.create(comment);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return result;
    }
}
