package com.exqzore.intacle.model.dao.impl;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.model.dao.EntryDao;
import com.exqzore.intacle.model.entity.Entry;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntryDaoImpl implements EntryDao {
    private final static Logger logger = LogManager.getLogger();

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static EntryDao instance = new EntryDaoImpl();

    private static final String CREATE_ENTRY = """
            INSERT INTO entries (title, author, summary, content, preview_image_path, creation_date, update_date)
            VALUES (?,?,?,?,?,?,?)
            """;
    private final static String UPDATE_ENTRY = """
            UPDATE entries SET summary = ?, content = ?, preview_image_path = ?, update_date=? WHERE id = ?
            """;
    private final static String REMOVE_ENTRY = "DELETE FROM entries WHERE id = ?";
    private final static String FIND_ENTRIES_BY_USER_LOGIN = """
            SELECT e.id, e.title, e.summary, e.content, e.creation_date, e.update_date, e.preview_image_path, e.author,
            u.login, u.avatar_image_path, COUNT(l.author),
            (SELECT IF(count(l.author) != 0, TRUE, FALSE) FROM likes l WHERE l.author = ? AND l.entry = e.id)
            FROM entries e
            JOIN users u ON e.author = u.id
            LEFT JOIN likes l ON e.id = l.entry
            WHERE u.login = ? GROUP BY e.id ORDER BY e.update_date DESC
            """;
    private final static String FIND_ENTRIES_BY_USER_SUBSCRIPTION = """
            SELECT e.id, e.title, e.summary, e.content, e.creation_date, e.update_date, e.preview_image_path, e.author,
            u.login, u.avatar_image_path, COUNT(l.author),
            (SELECT IF(count(l.author) != 0, TRUE, FALSE) FROM likes l WHERE l.author = ? AND l.entry = e.id)
            FROM entries e
            JOIN users u ON e.author = u.id
            LEFT JOIN likes l ON e.id = l.entry
            WHERE u.id = (SELECT s.subscription FROM subscription s WHERE s.ubscriber = ?) 
            GROUP BY e.id ORDER BY e.update_date DESC
            """;
    private final static String FIND_ENTRY_BY_ID = """
            SELECT e.id, e.title, e.summary, e.content, e.creation_date, e.update_date, e.preview_image_path, e.author,
            u.login, u.avatar_image_path, COUNT(l.author),
            (SELECT IF(count(l.author) != 0, TRUE, FALSE) FROM likes l WHERE l.author = ? AND l.entry = e.id)
            FROM entries e
            JOIN users u ON e.author = u.id
            LEFT JOIN likes l ON e.id = l.entry
            WHERE e.id = ? GROUP BY e.id ORDER BY e.update_date DESC
            """;
    private final static String LIKE_ENTRY = "INSERT INTO likes (author, entry) VALUES (?,?)";
    private final static String UNLIKE_ENTRY = "DELETE FROM likes WHERE author = ? AND entry = ?";

    private EntryDaoImpl() {
    }

    public static EntryDao getInstance() {
        return instance;
    }

    @Override
    public List<Entry> findByUserLogin(String login, long userId) throws DaoException {
        List<Entry> entries = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ENTRIES_BY_USER_LOGIN)) {
            statement.setLong(1, userId);
            statement.setString(2, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entries.add(entryFromResultSet(resultSet));
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Find entries error", exception);
            throw new DaoException(exception);
        }
        return entries;
    }

    @Override
    public List<Entry> findByUserSubscription(long userId) throws DaoException {
        List<Entry> entries = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ENTRIES_BY_USER_SUBSCRIPTION)) {
            statement.setLong(1, userId);
            statement.setLong(2, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entries.add(entryFromResultSet(resultSet));
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Find entries error", exception);
            throw new DaoException(exception);
        }
        return entries;
    }

    @Override
    public Optional<Entry> findById(long entryId, long userId) throws DaoException {
        Optional<Entry> optionalEntry;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ENTRY_BY_ID)) {
            statement.setLong(1, userId);
            statement.setLong(2, entryId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                optionalEntry = Optional.of(entryFromResultSet(resultSet));
                logger.log(Level.INFO, "Entry with id '{}' found successfully", entryId);
            } else {
                optionalEntry = Optional.empty();
                logger.log(Level.INFO, "Entry with id '{}' is not found", entryId);
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Entry found error", exception);
            throw new DaoException(exception);
        }
        return optionalEntry;
    }

    @Override
    public boolean create(Entry entry) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_ENTRY)) {
            statement.setString(1, entry.getTitle());
            statement.setLong(2, entry.getAuthor().getId());
            statement.setString(3, entry.getSummary());
            statement.setString(4, entry.getContent());
            statement.setString(5, entry.getPreviewImagePath());
            statement.setTimestamp(6, new Timestamp(entry.getCreationDate().getTime()));
            statement.setTimestamp(7, new Timestamp(entry.getUpdateDate().getTime()));
            if (statement.executeUpdate() > 0) {
                logger.log(Level.INFO, "New entry '{}' is created", entry);
                result = true;
            } else {
                logger.log(Level.INFO, "Entry '{}' is not created", entry);
                result = false;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Entry creation error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public boolean update(Entry entry) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ENTRY)) {
            statement.setString(1, entry.getSummary());
            statement.setString(2, entry.getContent());
            statement.setString(3, entry.getPreviewImagePath());
            statement.setTimestamp(4, new Timestamp(entry.getUpdateDate().getTime()));
            statement.setLong(5, entry.getId());
            if (statement.executeUpdate() > 0) {
                logger.log(Level.INFO, "Entry '{}' is successfully updated", entry);
                result = true;
            } else {
                logger.log(Level.INFO, "Entry '{}' is not updated", entry);
                result = false;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Entry update error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public boolean removeById(long entryId) throws DaoException {
        boolean result;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_ENTRY)) {
            statement.setLong(1, entryId);
            if (statement.executeUpdate() > 0) {
                logger.log(Level.INFO, "Entry '{}' is successfully deleted", entryId);
                result = true;
            } else {
                logger.log(Level.INFO, "Entry '{}' is not delete", entryId);
                result = false;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Entry delete error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public boolean like(long entryId, long userId) throws DaoException {
        boolean result;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(LIKE_ENTRY)) {
            statement.setLong(1, userId);
            statement.setLong(2, entryId);
            if (statement.executeUpdate() > 0) {
                logger.log(Level.INFO, "User '{}' like entry '{}'", userId, entryId);
                result = true;
            } else {
                logger.log(Level.INFO, "User '{}' could not like entry '{}'", userId, entryId);
                result = false;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Entry like error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public boolean unlike(long entryId, long userId) throws DaoException {
        boolean result;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UNLIKE_ENTRY)) {
            statement.setLong(1, userId);
            statement.setLong(2, entryId);
            if (statement.executeUpdate() > 0) {
                logger.log(Level.INFO, "User '{}' unlike entry '{}'", userId, entryId);
                result = true;
            } else {
                logger.log(Level.INFO, "User '{}' could not unlike entry '{}'", userId, entryId);
                result = false;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Entry unlike error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    private Entry entryFromResultSet(ResultSet resultSet) throws SQLException {
        User author = new User();
        author.setId(resultSet.getLong(8));
        author.setLogin(resultSet.getString(9));
        author.setAvatarImagePath(resultSet.getString(10));
        Entry entry = new Entry();
        entry.setAuthor(author);
        entry.setId(resultSet.getLong(1));
        entry.setTitle(resultSet.getString(2));
        entry.setSummary(resultSet.getString(3));
        entry.setContent(resultSet.getString(4));
        entry.setCreationDate(new Date(resultSet.getTimestamp(5).getTime()));
        entry.setUpdateDate(new Date(resultSet.getTimestamp(6).getTime()));
        entry.setPreviewImagePath(resultSet.getString(7));
        entry.setLikesCount(resultSet.getInt(11));
        entry.setLiked(resultSet.getBoolean(12));
        return entry;
    }
}
