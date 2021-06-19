package com.exqzore.intacle.service.impl;

import com.exqzore.intacle.dao.EntryDao;
import com.exqzore.intacle.entity.Entry;
import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.dao.impl.EntryDaoImpl;
import com.exqzore.intacle.entity.User;
import com.exqzore.intacle.service.EntryService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class EntryServiceImpl implements EntryService {
    private static final Logger logger = LogManager.getLogger();

    private static final EntryService instance = new EntryServiceImpl();

    private static final EntryDao entryDao = EntryDaoImpl.getInstance();

    private static final String DEFAULT_PREVIEW_IMAGE_PATH = "empty_image.png";

    private EntryServiceImpl() {
    }

    public static EntryService getInstance() {
        return instance;
    }

    @Override
    public List<Entry> findByUserLogin(String login, long userId) throws ServiceException {
        List<Entry> entries;
        try {
            entries = entryDao.findByUserLogin(login, userId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return entries;
    }

    @Override
    public List<Entry> findByUserSubscription(long userId) throws ServiceException {
        List<Entry> entries;
        try {
            entries = entryDao.findByUserSubscription(userId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return entries;
    }

    @Override
    public Optional<Entry> findById(long entryId, long userId) throws ServiceException {
        Optional<Entry> entryOptional;
        try {
            entryOptional = entryDao.findById(entryId, userId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return entryOptional;
    }

    @Override
    public Optional<Entry> create(String title, String summary, String content, String previewImagePath, User author)
            throws ServiceException {
        Optional<Entry> entryOptional;
        try {
            Entry entry = new Entry();
            entry.setAuthor(author);
            entry.setTitle(title);
            entry.setSummary(summary);
            entry.setContent(content);
            entry.setCreationDate(new Date());
            entry.setUpdateDate(entry.getCreationDate());
            if (previewImagePath.equals(DEFAULT_PREVIEW_IMAGE_PATH)) {
                entry.setPreviewImagePath(null);
            } else {
                entry.setPreviewImagePath(previewImagePath);
            }
            Optional<Entry> tmpEntryOptional = entryDao.create(entry);
            if (tmpEntryOptional.isPresent()) {
                entry.setId(tmpEntryOptional.get().getId());
                entryOptional = Optional.of(entry);
            } else {
                entryOptional = Optional.empty();
            }
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return entryOptional;
    }

    @Override
    public boolean edit(long entryId, String title, String summary, String content, String previewImagePath) throws ServiceException {
        boolean isEdited;
        try {
            Entry entry = new Entry();
            entry.setId(entryId);
            entry.setTitle(title);
            entry.setSummary(summary);
            entry.setContent(content);
            entry.setPreviewImagePath(previewImagePath);
            entry.setUpdateDate(new Date());
            isEdited = entryDao.update(entry);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return isEdited;
    }

    @Override
    public boolean removeById(long entryId) throws ServiceException {
        boolean isDeleted;
        try {
            isDeleted = entryDao.removeById(entryId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return isDeleted;
    }

    @Override
    public boolean like(long entryId, long userId) throws ServiceException {
        boolean isLiked;
        try {
            isLiked = entryDao.like(entryId, userId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return isLiked;
    }

    @Override
    public boolean unlike(long entryId, long userId) throws ServiceException {
        boolean isUnliked;
        try {
            isUnliked = entryDao.unlike(entryId, userId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return isUnliked;
    }
}
