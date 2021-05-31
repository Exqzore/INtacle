package com.exqzore.intacle.model.service.impl;

import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.dao.SubscriberDao;
import com.exqzore.intacle.model.dao.impl.SubscriberDaoImpl;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.service.SubscriberService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SubscriberServiceImpl implements SubscriberService {
    private final static Logger logger = LogManager.getLogger();

    private static final SubscriberService instance = new SubscriberServiceImpl();

    private final SubscriberDao subscriberDao = SubscriberDaoImpl.getInstance();

    private SubscriberServiceImpl() {
    }

    public static SubscriberService getInstance() {
        return instance;
    }

    @Override
    public boolean isSubscribe(long subscriberId, long subscriptionId) throws ServiceException {
        return false;
    }

    @Override
    public boolean subscribe(long subscriberId, long subscriptionId) throws ServiceException {
        return false;
    }

    @Override
    public boolean unsubscribe(long subscriberId, long subscriptionId) throws ServiceException {
        return false;
    }

    @Override
    public List<User> findUserSubscribersByTwenty(long userId, int offset) throws ServiceException {
        return null;
    }

    @Override
    public List<User> findUserSubscriptionsByTwenty(long userId, int offset) throws ServiceException {
        return null;
    }
}
