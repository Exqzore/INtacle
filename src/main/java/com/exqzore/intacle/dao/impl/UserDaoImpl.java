package com.exqzore.intacle.dao.impl;

import com.exqzore.intacle.dao.UserDao;
import com.exqzore.intacle.entity.User;
import com.exqzore.intacle.entity.UserRole;
import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.dao.connection.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private final static Logger logger = LogManager.getLogger();

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static UserDao instance = new UserDaoImpl();

    private static final String ADMIN = "ADMIN";
    private static final String USER_REGISTRATION = """
            INSERT INTO users (login, email, password, name, surname, user_level, activation_code, avatar_image_path)
            VALUES (?,?,?,?,?,?,?,?)
            """;
    private final static String USER_BY_LOGIN_AND_PASSWORD = """
            SELECT u.id, u.login, u.email, u.name, u.surname, u.avatar_image_path, u.user_level, u.activation_code,
            COUNT(s.subscriber), (SELECT COUNT(s2.subscription) FROM subscriptions s2 WHERE s2.subscriber = u.id)
            FROM users u
            LEFT JOIN subscriptions s ON u.id = s.subscription
            WHERE u.login = ? AND u.password = ? GROUP BY u.login
            """;
    private final static String LOGIN_WITH_ACTIVATION_CODE_EXISTS = """
            SELECT COUNT(login) FROM users WHERE login = ? AND activation_code = ?
            """;
    private final static String USER_ACTIVATION = """
            UPDATE users SET activation_code = NULL WHERE login = ? AND activation_code = ?
            """;
    private final static String USER_UPDATE = """
            UPDATE users SET name = ?, surname = ? WHERE login = ?
            """;
    private final static String USER_IMAGE_PATH_UPDATE = """
            UPDATE users SET avatar_image_path = ? WHERE id = ?
            """;
    private final static String FIND_BY_LOGIN = """
            SELECT u.id, u.login, u.email, u.name, u.surname, u.avatar_image_path, u.user_level, u.activation_code,
            COUNT(s.subscriber), (SELECT COUNT(s2.subscription) FROM subscriptions s2 WHERE s2.subscriber = u.id)
            FROM users u
            LEFT JOIN subscriptions s ON u.id = s.subscription
            WHERE u.login = ? GROUP BY u.login
            """;
    private final static String LOGIN_EXISTS = "SELECT COUNT(login) FROM users WHERE login = ?";
    private final static String FIND_BY_PATTERN = """   
            SELECT id, login, avatar_image_path FROM users WHERE login LIKE ? ORDER BY login
            """;
    private final static String FIND_NOT_ADMINS = """   
            SELECT id, login, user_level FROM users WHERE user_level != ?
            """;
    private final static String CHANGE_ROLE = """   
            UPDATE users SET user_level = ? WHERE id = ?
            """;

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
            statement.setString(6, user.getRole().name());
            statement.setString(7, user.getActivationCode());
            statement.setString(8, user.getAvatarImagePath());
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
                optionalUser = Optional.of(userFromResultSet(resultSet));
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
    public List<User> findByPattern(String pattern) throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_PATTERN)) {
            statement.setString(1, pattern);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setAvatarImagePath(resultSet.getString(3));
                users.add(user);
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Find users error", exception);
            throw new DaoException(exception);
        }
        return users;
    }

    @Override
    public List<User> findAllNotAdmins() throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_NOT_ADMINS)) {
            statement.setString(1, ADMIN);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setRole(UserRole.valueOf(resultSet.getString(3)));
                users.add(user);
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Find users error", exception);
            throw new DaoException(exception);
        }
        return users;
    }

    @Override
    public boolean editImagePath(String imagePath, long userId) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_IMAGE_PATH_UPDATE)) {
            statement.setString(1, imagePath);
            statement.setLong(2, userId);
            if (statement.executeUpdate() > 0) {
                logger.log(Level.INFO, "Image path is updated successfully");
                result = true;
            } else {
                logger.log(Level.INFO, "Image path is not updated");
                result = false;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "User update error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public boolean changeRole(String userRole, long userId) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CHANGE_ROLE)) {
            statement.setString(1, userRole);
            statement.setLong(2, userId);
            if (statement.executeUpdate() > 0) {
                logger.log(Level.INFO, "User role is changed successfully");
                result = true;
            } else {
                logger.log(Level.INFO, "User role is not changed");
                result = false;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "User update error", exception);
            throw new DaoException(exception);
        }
        return result;
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
    public boolean edit(String login, String name, String surname) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_UPDATE)) {
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, login);
            if (statement.executeUpdate() > 0) {
                logger.log(Level.INFO, "User '{}' is successfully updated", login);
                result = true;
            } else {
                logger.log(Level.INFO, "User '{}' is not updated", login);
                result = false;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "User update error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) throws DaoException {
        Optional<User> optionalUser;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement getUserStatement = connection.prepareStatement(USER_BY_LOGIN_AND_PASSWORD)) {
            getUserStatement.setString(1, login);
            getUserStatement.setString(2, password);
            ResultSet resultSet = getUserStatement.executeQuery();
            if (resultSet.next()) {
                optionalUser = Optional.of(userFromResultSet(resultSet));
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

    private User userFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(1));
        user.setLogin(resultSet.getString(2));
        user.setEmail(resultSet.getString(3));
        user.setName(resultSet.getString(4));
        user.setSurname(resultSet.getString(5));
        user.setAvatarImagePath(resultSet.getString(6));
        user.setRole(UserRole.valueOf(resultSet.getString(7)));
        user.setActivationCode(resultSet.getString(8));
        user.setSubscribersCount(resultSet.getInt(9));
        user.setSubscriptionsCount(resultSet.getInt(10));
        return user;
    }
}
