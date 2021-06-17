package com.exqzore.intacle.model.service.impl;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.exception.InvalidParamsException;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.dao.CommentDao;
import com.exqzore.intacle.model.dao.impl.CommentDaoImpl;
import com.exqzore.intacle.model.entity.Comment;
import com.exqzore.intacle.model.entity.Entry;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.service.CommentService;
import com.exqzore.intacle.model.validator.LoginValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public Optional<Comment> create(long entryId, String content, long authorId, String authorLogin,
                                    String authorAvatarImagePath) throws ServiceException {
        Optional<Comment> commentOptional;
        if (LoginValidator.checkLogin(authorLogin)) {
            try {
                Entry entry = new Entry();
                entry.setId(entryId);
                User author = new User();
                author.setId(authorId);
                author.setLogin(authorLogin);
                author.setAvatarImagePath(authorAvatarImagePath);
                Comment comment = new Comment();
                comment.setContent(content);
                comment.setCreationDate(new Date());
                comment.setUpdateDate(comment.getCreationDate());
                comment.setEntry(entry);
                comment.setAuthor(author);
                if (commentDao.create(comment)) {
                    commentOptional = Optional.of(comment);
                } else {
                    commentOptional = Optional.empty();
                }
            } catch (DaoException exception) {
                logger.log(Level.ERROR, exception);
                throw new ServiceException(exception);
            }
        } else {
            throw new InvalidParamsException();
        }
        return commentOptional;
    }
}
