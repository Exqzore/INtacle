package com.exqzore.intacle.model.dao.impl;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.model.dao.UserDao;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private final static Logger logger = LogManager.getLogger();

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static UserDao instance = new UserDaoImpl();

    private static final String USER_REGISTRATION = """
            INSERT INTO users (login, email, password, name, surname, user_level, activation_code, avatar_image_path)
            VALUES (?,?,?,?,?,?,?,?)
            """;
    private final static String USER_BY_LOGIN_AND_PASSWORD = """
            SELECT u.id, u.email, u.name, u.surname, u.avatar_image_path, u.user_level, u.activation_code,
            COUNT(s.subscriber), (SELECT COUNT(s2.subscription) FROM subscriptions s2 WHERE s2.subscriber = u.id)
            FROM users u
            LEFT JOIN subscriptions s ON u.id = s.subscription
            WHERE u.login = ? AND u.password = ?
            """;
    private final static String LOGIN_WITH_ACTIVATION_CODE_EXISTS = """
            SELECT COUNT(login) FROM users WHERE login = ? AND activation_code = ?
            """;
    private final static String USER_ACTIVATION = """
            UPDATE users SET activation_code = NULL WHERE login = ? AND activation_code = ?
            """;
    private final static String FIND_BY_LOGIN = """
            SELECT u.id, u.email, u.name, u.surname, u.avatar_image_path, u.user_level, u.activation_code,
            COUNT(s.subscriber), (SELECT COUNT(s2.subscription) FROM subscriptions s2 WHERE s2.subscriber = u.id)
            FROM users u
            LEFT JOIN subscriptions s ON u.id = s.subscription
            WHERE u.login = ?
            """;
    private final static String LOGIN_EXISTS = "SELECT COUNT(login) FROM users WHERE login = ?";

    private UserDaoImpl() {
    }

    public static UserDao getInstance() {
        return instance;
    }

    @Override
    public boolean create(User user) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_REGISTRATION)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getName());
            statement.setString(5, user.getSurname());
            statement.setString(6, user.getLevel());
            statement.setString(7, user.getActivationCode());
            statement.setString(8, user.getAvatarPath());
            if (statement.executeUpdate() > 0) {
                logger.log(Level.INFO, "New user '{}' is created", user);
                result = true;
            } else {
                logger.log(Level.INFO, "User '{}' is not created", user);
                result = false;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "User creation error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public boolean isLoginFree(String login) throws DaoException {
        boolean result;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(LOGIN_EXISTS)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next() && resultSet.getInt(1) == 0;
            if (result) {
                logger.log(Level.INFO, "Login '{}' is free", login);
            } else {
                logger.log(Level.INFO, "Login '{}' is busy", login);
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Login busy check error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        Optional<User> optionalUser;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setLogin(login);
                user.setEmail(resultSet.getString(2));
                user.setName(resultSet.getString(3));
                user.setSurname(resultSet.getString(4));
                user.setAvatarPath(resultSet.getString(5));
                user.setLevel(resultSet.getString(6));
                user.setActivationCode(resultSet.getString(7));
                user.setSubscribersCount(resultSet.getInt(8));
                user.setSubscriptionsCount(resultSet.getInt(9));
                optionalUser = Optional.of(user);
                logger.log(Level.INFO, "User with login '{}' found successfully", login);
            } else {
                optionalUser = Optional.empty();
                logger.log(Level.INFO, "User with login '{}' is not found", login);
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "User found error", exception);
            throw new DaoException(exception);
        }
        return optionalUser;
    }

    @Override
    public boolean activate(String login, String activationCode) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement checkLoginStatement = connection.prepareStatement(LOGIN_WITH_ACTIVATION_CODE_EXISTS);
             PreparedStatement activateStatement = connection.prepareStatement(USER_ACTIVATION)) {
            checkLoginStatement.setString(1, login);
            checkLoginStatement.setString(2, activationCode);
            ResultSet resultSet = checkLoginStatement.executeQuery();
            result = resultSet.next() && resultSet.getInt(1) == 1;
            if (result) {
                activateStatement.setString(1, login);
                activateStatement.setString(2, activationCode);
                activateStatement.execute();
                logger.log(Level.INFO, "User with login '{}' successfully activated", login);
            } else {
                logger.log(Level.INFO, "User with login '{}' not activated", login);
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "User activation error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public Optional<User> login(String login, String password) throws DaoException {
        Optional<User> optionalUser;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement getUserStatement = connection.prepareStatement(USER_BY_LOGIN_AND_PASSWORD)) {
            getUserStatement.setString(1, login);
            getUserStatement.setString(2, password);
            ResultSet resultSet = getUserStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setLogin(login);
                user.setEmail(resultSet.getString(2));
                user.setName(resultSet.getString(3));
                user.setSurname(resultSet.getString(4));
                user.setAvatarPath(resultSet.getString(5));
                user.setLevel(resultSet.getString(6));
                user.setActivationCode(resultSet.getString(7));
                user.setSubscribersCount(resultSet.getInt(8));
                user.setSubscriptionsCount(resultSet.getInt(9));
                optionalUser = Optional.of(user);
                logger.log(Level.INFO, "User with login '{}' successfully authenticated", login);
            } else {
                optionalUser = Optional.empty();
                logger.log(Level.INFO, "User with login '{}' failed authentication", login);
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "User login error", exception);
            throw new DaoException(exception);
        }
        return optionalUser;
    }
}
