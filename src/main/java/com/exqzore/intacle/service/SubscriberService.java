package com.exqzore.intacle.service;

import com.exqzore.intacle.entity.User;
import com.exqzore.intacle.exception.ServiceException;

import java.util.List;

public interface SubscriberService {
    boolean isSubscribe(long subscriberId, long subscriptionId) throws ServiceException;

    boolean subscribe(String subscriberLogin, String subscriptionLogin) throws ServiceException;

    boolean unsubscribe(String subscriberLogin, String subscriptionLogin) throws ServiceException;

    List<User> findUserSubscribers(String login) throws ServiceException;

    List<User> findUserSubscriptions(String login) throws ServiceException;
}
