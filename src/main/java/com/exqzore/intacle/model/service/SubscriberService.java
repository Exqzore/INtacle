package com.exqzore.intacle.model.service;

import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.User;

import java.util.List;

public interface SubscriberService {
    boolean isSubscribe(long subscriberId, long subscriptionId) throws ServiceException;

    boolean subscribe(long subscriberId, long subscriptionId) throws ServiceException;

    boolean unsubscribe(long subscriberId, long subscriptionId) throws ServiceException;

    List<User> findUserSubscribersByTwenty(long userId, int offset) throws ServiceException;

    List<User> findUserSubscriptionsByTwenty(long userId, int offset) throws ServiceException;
}
