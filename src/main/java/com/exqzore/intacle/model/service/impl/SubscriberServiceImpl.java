package com.exqzore.intacle.model.service.impl;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.exception.InvalidParamsException;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.dao.SubscriberDao;
import com.exqzore.intacle.model.dao.impl.SubscriberDaoImpl;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.service.SubscriberService;
import com.exqzore.intacle.model.validator.LoginValidator;
import org.apache.logging.log4j.Level;
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
        boolean isSubscribe;
        try {
            isSubscribe = subscriberDao.isSubscribe(subscriberId, subscriptionId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return isSubscribe;
    }

    @Override
    public boolean subscribe(String subscriberLogin, String subscriptionLogin) throws ServiceException {
        boolean isSubscribed;
        if (LoginValidator.checkLogin(subscriberLogin) && LoginValidator.checkLogin(subscriptionLogin)) {
            try {
                isSubscribed = subscriberDao.subscribe(subscriberLogin, subscriptionLogin);
            } catch (DaoException exception) {
                logger.log(Level.ERROR, exception);
                throw new ServiceException(exception);
            }
        } else {
            throw new InvalidParamsException();
        }
        return isSubscribed;
    }

    @Override
    public boolean unsubscribe(String subscriberLogin, String subscriptionLogin) throws ServiceException {
        boolean isUnsubscribed;
        if (LoginValidator.checkLogin(subscriberLogin) && LoginValidator.checkLogin(subscriptionLogin)) {
            try {
                isUnsubscribed = subscriberDao.unsubscribe(subscriberLogin, subscriptionLogin);
            } catch (DaoException exception) {
                logger.log(Level.ERROR, exception);
                throw new ServiceException(exception);
            }
        } else {
            throw new InvalidParamsException();
        }
        return isUnsubscribed;
    }

    @Override
    public List<User> findUserSubscribers(String login) throws ServiceException {
        List<User> users;
        if (LoginValidator.checkLogin(login)) {
            try {
                users = subscriberDao.findUserSubscribers(login);
            } catch (DaoException exception) {
                logger.log(Level.ERROR, exception);
                throw new ServiceException(exception);
            }
        } else {
            throw new InvalidParamsException();
        }
        return users;
    }

    @Override
    public List<User> findUserSubscriptions(String login) throws ServiceException {
        List<User> users;
        if (LoginValidator.checkLogin(login)) {
            try {
                users = subscriberDao.findUserSubscriptions(login);
            } catch (DaoException exception) {
                logger.log(Level.ERROR, exception);
                throw new ServiceException(exception);
            }
        } else {
            throw new InvalidParamsException();
        }
        return users;
    }
}
