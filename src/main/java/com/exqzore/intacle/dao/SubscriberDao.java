package com.exqzore.intacle.dao;

import com.exqzore.intacle.entity.User;
import com.exqzore.intacle.exception.DaoException;

import java.util.List;

public interface SubscriberDao {
    boolean isSubscribe(long subscriberId, long subscriptionId) throws DaoException;

    boolean subscribe(String subscriberLogin, String subscriptionLogin) throws DaoException;

    boolean unsubscribe(String subscriberLogin, String subscriptionLogin) throws DaoException;

    List<User> findUserSubscribers(String login) throws DaoException;

    List<User> findUserSubscriptions(String login) throws DaoException;
}
