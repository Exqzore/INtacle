package com.exqzore.intacle.model.dao.impl;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.model.dao.SubscriberDao;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriberDaoImpl implements SubscriberDao {
    private final static Logger logger = LogManager.getLogger();

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static SubscriberDao instance = new SubscriberDaoImpl();

    private final static String IS_SUBSCRIBE = """
            SELECT COUNT(subscriber) FROM subscriptions WHERE subscriber = ? AND subscription = ?
            """;
    private final static String USER_SUBSCRIBERS = """
            SELECT u.id, u.login, u.avatar_image_path FROM users u LEFT JOIN subscriptions s ON u.id = s.subscriber
            WHERE s.subscription = (SELECT u2.id FROM users u2 WHERE u2.login = ?) ORDER BY u.login
            """;
    private final static String USER_SUBSCRIPTIONS = """
            SELECT u.id, u.login, u.avatar_image_path FROM users u LEFT JOIN subscriptions s ON u.id = s.subscription
            WHERE s.subscriber = (SELECT u2.id FROM users u2 WHERE u2.login = ?) ORDER BY u.login
            """;
    private final static String SUBSCRIBE = """
            INSERT INTO subscriptions (subscription, subscriber)
            VALUES ((SELECT u.id FROM users u WHERE u.login = ?),(SELECT u2.id FROM users u2 WHERE u2.login = ?))
            """;
    private final static String UNSUBSCRIBE = """
            DELETE FROM subscriptions
            WHERE subscription = (SELECT u.id FROM users u WHERE u.login = ?)
            AND subscriber = (SELECT u2.id FROM users u2 WHERE u2.login = ?)
            """;

    private SubscriberDaoImpl() {
    }

    public static SubscriberDao getInstance() {
        return instance;
    }

    @Override
    public boolean isSubscribe(long subscriberId, long subscriptionId) throws DaoException {
        boolean result;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(IS_SUBSCRIBE)) {
            statement.setLong(1, subscriberId);
            statement.setLong(2, subscriptionId);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next() && resultSet.getInt(1) != 0;
            if (result) {
                logger.log(Level.INFO, "User with id '{}' subscribed to user with id '{}'", subscriberId, subscriptionId);
            } else {
                logger.log(Level.INFO, "User with id '{}' not subscribed to user with id '{}'", subscriberId, subscriptionId);
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "IsSubscribe check error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public boolean subscribe(String subscriberLogin, String subscriptionLogin) throws DaoException {
        boolean result;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SUBSCRIBE)) {
            statement.setString(1, subscriptionLogin);
            statement.setString(2, subscriberLogin);
            if (statement.executeUpdate() > 0) {
                logger.log(Level.INFO, "User '{}' subscribed to user '{}'", subscriberLogin, subscriptionLogin);
                result = true;
            } else {
                logger.log(Level.INFO, "User '{}' could not subscribe to user '{}'", subscriberLogin, subscriptionLogin);
                result = false;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "User subscribe error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public boolean unsubscribe(String subscriberLogin, String subscriptionLogin) throws DaoException {
        boolean result;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UNSUBSCRIBE)) {
            statement.setString(1, subscriptionLogin);
            statement.setString(2, subscriberLogin);
            if (statement.executeUpdate() > 0) {
                logger.log(Level.INFO, "User '{}' unsubscribed to user '{}'", subscriberLogin, subscriptionLogin);
                result = true;
            } else {
                logger.log(Level.INFO, "User '{}' could not unsubscribe to user '{}'", subscriberLogin, subscriptionLogin);
                result = false;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "User unsubscribe error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public List<User> findUserSubscribers(String login) throws DaoException {
        List<User> subscribers = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_SUBSCRIBERS)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                subscribers.add(userFromResultSet(resultSet));
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Find user subscribers error", exception);
            throw new DaoException(exception);
        }
        return subscribers;
    }

    @Override
    public List<User> findUserSubscriptions(String login) throws DaoException {
        List<User> subscriptions = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_SUBSCRIPTIONS)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                subscriptions.add(userFromResultSet(resultSet));
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Find user subscriptions error", exception);
            throw new DaoException(exception);
        }
        return subscriptions;
    }

    private User userFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(1));
        user.setLogin(resultSet.getString(2));
        user.setAvatarImagePath(resultSet.getString(3));
        return user;
    }
}
