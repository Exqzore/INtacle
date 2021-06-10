package com.exqzore.intacle.model.dao;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.model.entity.User;

import java.util.List;

public interface SubscriberDao {
    boolean isSubscribe(long subscriberId, long subscriptionId) throws DaoException;

    boolean subscribe(String subscriberLogin, String subscriptionLogin) throws DaoException;

    boolean unsubscribe(String subscriberLogin, String subscriptionLogin) throws DaoException;

    List<User> findSubscribers(String login, int count, int offset) throws DaoException;

    List<User> findSubscriptions(String login, int count, int offset) throws DaoException;
}
