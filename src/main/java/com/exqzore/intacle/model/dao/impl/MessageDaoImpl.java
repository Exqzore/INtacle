package com.exqzore.intacle.model.dao.impl;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.model.dao.MessageDao;
import com.exqzore.intacle.model.entity.Chat;
import com.exqzore.intacle.model.entity.Message;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.entity.UserRole;
import com.exqzore.intacle.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageDaoImpl implements MessageDao {
    private final static Logger logger = LogManager.getLogger();

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static MessageDao instance = new MessageDaoImpl();

    private static final String CREATE_MESSAGE = """
            INSERT INTO messages (author, chat, content, creation_date, update_date, is_new) VALUES (?,?,?,?,?,?)
            """;
    private final static String UPDATE_MESSAGE = """
            UPDATE messages SET content = ?, update_date = ? WHERE id = ?
            """;
    private final static String FIND_ALL_MESSAGES_BY_CHAT_ID = """
            SELECT m.id, m.chat, m.author, u.login, u.avatar_image_path, m.content, m.creation_date
            FROM messages AS m
            LEFT JOIN users AS u ON m.author = u.id WHERE m.chat = ?
            ORDER BY m.creation_date
            """;
    private final static String FIND_NEW_NOT_SENDER_MESSAGES_BY_CHAT_ID = """
            SELECT m.id, m.chat, m.author, u.login, u.avatar_image_path, m.content, m.creation_date
            FROM messages AS m
            LEFT JOIN users AS u ON m.author = u.id WHERE m.chat = ? AND m.author != ? AND m.is_new = 1
            ORDER BY m.creation_date
            """;
    private final static String DEFINE_OLD_MESSAGES = """
            UPDATE messages SET is_new = 0 WHERE chat = ? AND author != ?
            """;

    private MessageDaoImpl() {
    }

    public static MessageDao getInstance() {
        return instance;
    }

    @Override
    public boolean create(Message message) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_MESSAGE)) {
            statement.setLong(1, message.getAuthor().getId());
            statement.setLong(2, message.getChat().getId());
            statement.setString(3, message.getContent());
            statement.setTimestamp(4, new Timestamp(message.getCreationDate().getTime()));
            statement.setTimestamp(5, new Timestamp(message.getUpdateDate().getTime()));
            statement.setBoolean(6, true);
            if (statement.executeUpdate() > 0) {
                logger.log(Level.INFO, "New message '{}' is added to chat with id '{}'", message, message.getChat().getId());
                result = true;
            } else {
                logger.log(Level.INFO, "Message '{}' is not added to chat with id '{}'", message, message.getChat().getId());
                result = false;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Message creation error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public boolean update(Message message) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_MESSAGE)) {
            statement.setString(1, message.getContent());
            statement.setTimestamp(2, new Timestamp(message.getUpdateDate().getTime()));
            statement.setLong(3, message.getId());
            if (statement.executeUpdate() > 0) {
                logger.log(Level.INFO, "Message '{}' is successfully updated", message);
                result = true;
            } else {
                logger.log(Level.INFO, "Message '{}' is not updated", message);
                result = false;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Message update error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public void defineOdl(long chatId, long senderId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DEFINE_OLD_MESSAGES)) {
            statement.setLong(1, chatId);
            statement.setLong(2, senderId);
            statement.execute();
            logger.log(Level.INFO, "Messages is outdated");
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Messages set outdated error", exception);
            throw new DaoException(exception);
        }
    }

    @Override
    public List<Message> findByChatId(long chatId) throws DaoException {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_MESSAGES_BY_CHAT_ID)) {
            statement.setLong(1, chatId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                messages.add(messageFromResultSet(resultSet));
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Find all messages error", exception);
            throw new DaoException(exception);
        }
        return messages;
    }

    @Override
    public List<Message> findNewNotSenderByChatId(long chatId, long senderId) throws DaoException {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_NEW_NOT_SENDER_MESSAGES_BY_CHAT_ID)) {
            statement.setLong(1, chatId);
            statement.setLong(2, senderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                messages.add(messageFromResultSet(resultSet));
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Find all messages error", exception);
            throw new DaoException(exception);
        }
        return messages;
    }

    private Message messageFromResultSet(ResultSet resultSet) throws SQLException {
        User author = new User();
        author.setId(resultSet.getLong(3));
        author.setLogin(resultSet.getString(4));
        author.setAvatarImagePath(resultSet.getString(5));
        Chat chat = new Chat();
        chat.setId(resultSet.getLong(2));
        Message message = new Message();
        message.setChat(chat);
        message.setAuthor(author);
        message.setId(resultSet.getLong(1));
        message.setContent(resultSet.getString(6));
        message.setCreationDate(new Date(resultSet.getTimestamp(7).getTime()));
        return message;
    }
}
