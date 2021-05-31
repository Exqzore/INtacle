package com.exqzore.intacle.model.dao;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.model.entity.User;

import java.util.List;

public interface SubscriberDao {
    boolean isSubscribe(long subscriberId, long subscriptionId) throws DaoException;

    boolean subscribe(long subscriberId, long subscriptionId) throws DaoException;

    boolean unsubscribe(long subscriberId, long subscriptionId) throws DaoException;

    List<User> findSubscribers(long userId, int count, int offset) throws DaoException;

    List<User> findSubscriptions(long userId, int count, int offset) throws DaoException;

    int findSubscribersCount(long userId) throws DaoException;

    int findSubscriptionsCount(long userId) throws DaoException;
}
