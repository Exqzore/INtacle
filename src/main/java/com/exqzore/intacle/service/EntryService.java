package com.exqzore.intacle.service;

import com.exqzore.intacle.entity.Entry;
import com.exqzore.intacle.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface EntryService {
    List<Entry> findByUserLogin(String login, long userId) throws ServiceException;

    List<Entry> findByUserSubscription(long userId) throws ServiceException;

    Optional<Entry> findById(long entryId, long userId) throws ServiceException;

    Optional<Entry> create(String title, String summary, String content, String previewImagePath, long authorId,
                           String authorLogin, String authorAvatarImagePath) throws ServiceException;

    boolean edit(long entryId, String summary, String content, String previewImagePath) throws ServiceException;

    boolean removeById(long entryId) throws ServiceException;

    boolean like(long entryId, long userId) throws ServiceException;

    boolean unlike(long entryId, long userId) throws ServiceException;
}
