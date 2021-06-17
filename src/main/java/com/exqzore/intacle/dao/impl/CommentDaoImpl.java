package com.exqzore.intacle.dao.impl;

import com.exqzore.intacle.dao.CommentDao;
import com.exqzore.intacle.entity.Comment;
import com.exqzore.intacle.entity.Entry;
import com.exqzore.intacle.entity.User;
import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.dao.connection.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentDaoImpl implements CommentDao {
    private final static Logger logger = LogManager.getLogger();

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static CommentDao instance = new CommentDaoImpl();

    private static final String CREATE_COMMENT = """
            INSERT INTO comments (author, content, creation_date, update_date, entry) VALUES (?,?,?,?,?)
            """;
    private final static String UPDATE_COMMENT = """
            UPDATE comments SET content = ?, update_date=? WHERE id = ?
            """;
    private final static String REMOVE_COMMENT = "DELETE FROM comments WHERE id = ?";
    private final static String FIND_COMMENTS_BY_ENTRY_ID = """
            SELECT c.id, c.content, c.creation_date, c.update_date, c.entry, c.author, u.login, u,avatar_image_path,
            COUNT(l.author),
            (SELECT IF(count(l.author) != 0, TRUE, FALSE) FROM likes l WHERE l.author = ? AND l.comment = c.id)
            FROM comments c
            JOIN users u ON c.author = u.id
            LEFT JOIN likes l ON c.id = l.comment
            WHERE c.entry = ? GROUP BY c.id
            """;
    private final static String LIKE_COMMENT = "INSERT INTO likes (author, comment) VALUES (?,?)";
    private final static String UNLIKE_COMMENT = "DELETE FROM likes WHERE author = ? AND comment = ?";

    private CommentDaoImpl() {
    }

    public static CommentDao getInstance() {
        return instance;
    }

    @Override
    public boolean create(Comment comment) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_COMMENT)) {
            statement.setLong(1, comment.getAuthor().getId());
            statement.setString(2, comment.getContent());
            statement.setLong(5, comment.getEntry().getId());
            statement.setTimestamp(3, new Timestamp(comment.getCreationDate().getTime()));
            statement.setTimestamp(4, new Timestamp(comment.getUpdateDate().getTime()));
            if (statement.executeUpdate() > 0) {
                logger.log(Level.INFO, "New comment '{}' is created", comment);
                result = true;
            } else {
                logger.log(Level.INFO, "Comment '{}' is not created", comment);
                result = false;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Comment creation error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public boolean update(Comment comment) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_COMMENT)) {
            statement.setString(1, comment.getContent());
            statement.setTimestamp(2, new Timestamp(comment.getUpdateDate().getTime()));
            statement.setLong(3, comment.getId());
            if (statement.executeUpdate() > 0) {
                logger.log(Level.INFO, "Comment '{}' is successfully updated", comment);
                result = true;
            } else {
                logger.log(Level.INFO, "Comment '{}' is not updated", comment);
                result = false;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Comment update error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public List<Comment> findByEntryId(long entryId, long userId) throws DaoException {
        List<Comment> comments = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_COMMENTS_BY_ENTRY_ID)) {
            statement.setLong(1, userId);
            statement.setLong(2, entryId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Entry entry = new Entry();
                entry.setId(resultSet.getLong(5));
                User author = new User();
                author.setId(resultSet.getLong(6));
                author.setLogin(resultSet.getString(7));
                author.setAvatarImagePath(resultSet.getString(8));
                Comment comment = new Comment();
                comment.setId(resultSet.getLong(1));
                comment.setContent(resultSet.getString(2));
                comment.setCreationDate(new Date(resultSet.getTimestamp(3).getTime()));
                comment.setUpdateDate(new Date(resultSet.getTimestamp(4).getTime()));
                comment.setEntry(entry);
                comment.setAuthor(author);
                comment.setLikesCount(resultSet.getInt(9));
                comment.setLiked(resultSet.getBoolean(10));
                comments.add(comment);
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Find comments error", exception);
            throw new DaoException(exception);
        }
        return comments;
    }

    @Override
    public boolean removeById(long commentId) throws DaoException {
        boolean result;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_COMMENT)) {
            statement.setLong(1, commentId);
            if (statement.executeUpdate() > 0) {
                logger.log(Level.INFO, "Comment '{}' is successfully deleted", commentId);
                result = true;
            } else {
                logger.log(Level.INFO, "Comment '{}' is not delete", commentId);
                result = false;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Comment delete error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public boolean like(long commentId, long userId) throws DaoException {
        boolean result;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(LIKE_COMMENT)) {
            statement.setLong(1, userId);
            statement.setLong(2, commentId);
            if (statement.executeUpdate() > 0) {
                logger.log(Level.INFO, "User '{}' like comment '{}'", userId, commentId);
                result = true;
            } else {
                logger.log(Level.INFO, "User '{}' could not like comment '{}'", userId, commentId);
                result = false;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Comment like error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public boolean unlike(long commentId, long userId) throws DaoException {
        boolean result;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UNLIKE_COMMENT)) {
            statement.setLong(1, userId);
            statement.setLong(2, commentId);
            if (statement.executeUpdate() > 0) {
                logger.log(Level.INFO, "User '{}' unlike comment '{}'", userId, commentId);
                result = true;
            } else {
                logger.log(Level.INFO, "User '{}' could not unlike comment '{}'", userId, commentId);
                result = false;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Comment unlike error", exception);
            throw new DaoException(exception);
        }
        return result;
    }
}
