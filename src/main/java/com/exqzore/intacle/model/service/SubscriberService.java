package com.exqzore.intacle.model.service;

import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.User;

import java.util.List;

public interface SubscriberService {
    boolean isSubscribe(long subscriberId, long subscriptionId) throws ServiceException;

    boolean subscribe(String subscriberLogin, String subscriptionLogin) throws ServiceException;

    boolean unsubscribe(String subscriberLogin, String subscriptionLogin) throws ServiceException;

    List<User> findUserSubscribers(String login) throws ServiceException;

    List<User> findUserSubscriptions(String login) throws ServiceException;
}
