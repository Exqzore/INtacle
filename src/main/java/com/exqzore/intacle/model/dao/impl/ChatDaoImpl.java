package com.exqzore.intacle.model.dao.impl;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.model.dao.ChatDao;
import com.exqzore.intacle.model.entity.Chat;
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
import java.util.Optional;

public class ChatDaoImpl implements ChatDao {
    private final static Logger logger = LogManager.getLogger();

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static ChatDao instance = new ChatDaoImpl();

    private final static String CREATE_CHAT = """
            INSERT INTO chats VALUES ();
            INSERT INTO chat_membership (chat, user) VALUES (LAST_INSERT_ID(), ?);
            INSERT INTO chat_membership (chat, user) VALUES (LAST_INSERT_ID(), ?)
            """;

    private final static String USER_CHATS = """
            SELECT t.id, u.id, u.login, u.avatar_image_path FROM (SELECT c2.id, cm2.user FROM chats c2
            LEFT JOIN chat_membership cm2 on c2.id = cm2.chat
            WHERE c2.id IN (SELECT c.id FROM chats c LEFT JOIN chat_membership cm ON c.id = cm.chat WHERE cm.user = ?)) t
            LEFT JOIN users u ON t.user = u.id
            WHERE u.id != ?
            """;

    private final static String CHAT_BY_USERS = """
            SELECT chat FROM chat_membership WHERE user = ? OR user = ? GROUP BY chat HAVING COUNT(*) = 2
            """;

    private ChatDaoImpl() {
    }

    public static ChatDao getInstance() {
        return instance;
    }

    @Override
    public boolean create(long firstUserId, long secondUserId) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_CHAT)) {
            statement.setLong(1, firstUserId);
            statement.setLong(2, secondUserId);
            if (statement.executeUpdate() > 0) {
                logger.log(Level.INFO, "New chat with user ids: '{}', '{}' is created", firstUserId, secondUserId);
                result = true;
            } else {
                logger.log(Level.INFO, "New chat with user ids: '{}', '{}' is not created", firstUserId, secondUserId);
                result = false;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Chat creation error", exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public Optional<Chat> findByUsers(long firstUserId, long secondUserId) throws DaoException {
        Optional<Chat> optionalChat;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHAT_BY_USERS)) {
            statement.setLong(1, firstUserId);
            statement.setLong(2, secondUserId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Chat chat = new Chat();
                chat.setId(resultSet.getLong(1));
                optionalChat = Optional.of(chat);
                logger.log(Level.INFO, "Chat with ids '{}' and '{}' found successfully", firstUserId, secondUserId);
            } else {
                optionalChat = Optional.empty();
                logger.log(Level.INFO, "Chat with ids '{}' and '{}' is not found", firstUserId, secondUserId);
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Chat found error", exception);
            throw new DaoException(exception);
        }
        return optionalChat;
    }

    @Override
    public List<Chat> findUserChat(long userId) throws DaoException {
        List<Chat> chats = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_CHATS)) {
            statement.setLong(1, userId);
            statement.setLong(2, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Chat chat = new Chat();
                chat.setId(resultSet.getLong(1));
                chat.setRecipientId(resultSet.getLong(2));
                chat.setRecipientLogin(resultSet.getString(3));
                chat.setRecipientAvatarPath(resultSet.getString(4));
                chats.add(chat);
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Find user chat error", exception);
            throw new DaoException(exception);
        }
        return chats;
    }
}
