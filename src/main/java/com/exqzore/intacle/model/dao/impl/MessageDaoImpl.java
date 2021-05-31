package com.exqzore.intacle.model.dao.impl;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.model.dao.MessageDao;
import com.exqzore.intacle.model.dao.UserDao;
import com.exqzore.intacle.model.entity.Message;
import com.exqzore.intacle.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class MessageDaoImpl implements MessageDao {
    private final static Logger logger = LogManager.getLogger();

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static MessageDao instance = new MessageDaoImpl();

    private static final String CREATE_MESSAGE = """
            INSERT INTO messages (author, chat, content, creation_date, update_date, is_new) VALUES (?,?,?,?,?,?)
            """;
    private final static String UPDATE_MESSAGE = """
            UPDATE messages SET content=?, update_date=? WHERE id=?
            """;

    private MessageDaoImpl() {
    }

    public static MessageDao getInstance() {
        return instance;
    }

    @Override
    public void create(Message message) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_MESSAGE)) {
            statement.setLong(1, message.getAuthorId());
            statement.setLong(2, message.getChatId());
            statement.setString(3, message.getContent());
            statement.setTimestamp(4, new Timestamp(message.getCreationDate().getTime()));
            statement.setTimestamp(5, new Timestamp(message.getUpdateDate().getTime()));
            statement.setBoolean(6, true);
            statement.execute();
            logger.log(Level.INFO, "New message '{}' is added to chat with id '{}'", message, message.getChatId());
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Message creation error", exception);
            throw new DaoException(exception);
        }
    }

    @Override
    public void update(Message message) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_MESSAGE)) {
            statement.setString(1, message.getContent());
            statement.setTimestamp(2, new Timestamp(message.getUpdateDate().getTime()));
            statement.setLong(3, message.getId());
            statement.execute();
            logger.log(Level.INFO, "Message '{}' is successfully updated", message);
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Message update error", exception);
            throw new DaoException(exception);
        }
    }

    @Override
    public List<Message> findByChatId(long chatId) throws DaoException {
        return null;
    }

    @Override
    public List<Message> findNewByChatId(long chatId) throws DaoException {
        return null;
    }
}
