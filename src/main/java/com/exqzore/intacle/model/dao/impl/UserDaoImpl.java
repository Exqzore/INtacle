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
    private final static Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static UserDaoImpl instance = new UserDaoImpl();

    private static final String USER_REGISTRATION = """
            INSERT INTO users (login, email, password, name, surname, user_level, activation_code) VALUES (?,?,?,?,?,?,?)
            """;
    private final static String USER_BY_LOGIN_AND_PASSWORD = """
            SELECT login, email, name, surname, avatar_image_path, user_level, activation_code FROM users WHERE login=? AND password=?
            """;
    private final static String LOGIN_WITH_ACTIVATION_CODE_EXISTS = """
            SELECT COUNT(login) FROM users WHERE login=? AND activation_code=?
            """;
    private final static String USER_ACTIVATION = """
            UPDATE users SET activation_code=null WHERE login=? AND activation_code=?
            """;
    private final static String LOGIN_EXISTS = "SELECT COUNT(login) FROM users WHERE login=?";


    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public void create(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_REGISTRATION)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getName().isEmpty() ? null : user.getName());
            statement.setString(5, user.getSurname().isEmpty() ? null : user.getSurname());
            statement.setString(6, user.getLevel());
            statement.setString(7, user.getActivationCode());
            statement.execute();
            logger.log(Level.INFO, "New user '{}' is created", user);
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "User creation error", exception);
            throw new DaoException(exception);
        }
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
    public Optional<User> login(String login, String password) throws DaoException {
        Optional<User> optionalUser;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString(1));
                user.setEmail(resultSet.getString(2));
                user.setName(resultSet.getString(3));
                user.setSurname(resultSet.getString(4));
                user.setAvatarPath(resultSet.getString(5));
                user.setLevel(resultSet.getString(6));
                user.setActivationCode(resultSet.getString(7));
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

    @Override
    public boolean activate(String login, String activationCode) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement1 = connection.prepareStatement(LOGIN_WITH_ACTIVATION_CODE_EXISTS);
             PreparedStatement statement2 = connection.prepareStatement(USER_ACTIVATION)) {
            statement1.setString(1, login);
            statement1.setString(2, activationCode);
            ResultSet resultSet = statement1.executeQuery();
            result = resultSet.next() && resultSet.getInt(1) == 1;
            if (result) {
                statement2.setString(1, login);
                statement2.setString(2, activationCode);
                statement2.execute();
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
}
