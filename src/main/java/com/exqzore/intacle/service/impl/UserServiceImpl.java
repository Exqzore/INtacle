package com.exqzore.intacle.service.impl;

import com.exqzore.intacle.dao.UserDao;
import com.exqzore.intacle.dao.impl.UserDaoImpl;
import com.exqzore.intacle.entity.User;
import com.exqzore.intacle.entity.UserRole;
import com.exqzore.intacle.exception.*;
import com.exqzore.intacle.service.UserService;
import com.exqzore.intacle.service.validator.LoginValidator;
import com.exqzore.intacle.service.validator.Validator;
import com.exqzore.intacle.service.util.PasswordEncoder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private final static Logger logger = LogManager.getLogger();

    private static final UserService instance = new UserServiceImpl();

    private final UserDao userDao = UserDaoImpl.getInstance();

    private static final String DEFAULT_AVATAR_IMAGE__PATH = "default_avatar.png";

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        return instance;
    }

    @Override
    public Optional<User> register(String login, String email, String password, String repeatPassword, String name,
                                   String surname) throws ServiceException {
        Optional<User> userOptional;
        boolean isLoginFree;
        try {
            isLoginFree = userDao.isLoginFree(login);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        if (!isLoginFree) {
            throw new UserLoginIsBusyException();
        }
        if (Validator.checkRegistration(login, email, password, repeatPassword, name, surname)) {
            try {
                User user = new User();
                user.setLogin(login);
                user.setEmail(email);
                user.setPassword(PasswordEncoder.encode(repeatPassword));
                user.setAvatarImagePath(DEFAULT_AVATAR_IMAGE__PATH);
                user.setRole(UserRole.USER);
                user.setName(name.isEmpty() ? null : name);
                user.setSurname(surname.isEmpty() ? null : surname);
                user.setActivationCode(UUID.randomUUID().toString());
                if (userDao.create(user)) {
                    userOptional = Optional.of(user);
                } else {
                    userOptional = Optional.empty();
                }
            } catch (DaoException | NoSuchAlgorithmException exception) {
                logger.log(Level.ERROR, exception);
                throw new ServiceException(exception);
            }
        } else {
            throw new InvalidParamsException();
        }
        return userOptional;
    }

    @Override
    public Optional<User> login(String login, String password) throws ServiceException {
        Optional<User> userOptional;
        if (Validator.checkLogIn(login, password)) {
            try {
                password = PasswordEncoder.encode(password);
                userOptional = userDao.findByLoginAndPassword(login, password);
            } catch (DaoException | NoSuchAlgorithmException exception) {
                logger.log(Level.ERROR, exception);
                throw new ServiceException(exception);
            }
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (user.getActivationCode() != null) {
                    throw new UserNotActivatedException();
                }
            } else {
                throw new InvalidParamsException();
            }
        } else {
            throw new InvalidParamsException();
        }
        return userOptional;
    }

    @Override
    public boolean activate(String login, String activationCode) throws ServiceException {
        boolean isActivated;
        if (Validator.checkActivateUser(login, activationCode)) {
            try {
                isActivated = userDao.activate(login, activationCode);
            } catch (DaoException exception) {
                logger.log(Level.ERROR, exception);
                throw new ServiceException(exception);
            }
        } else {
            throw new InvalidParamsException();
        }
        return isActivated;
    }

    @Override
    public boolean edit(String login, String name, String surname) throws ServiceException {
        boolean isEdited;
        if (Validator.checkEdit(name, surname)) {
            try {
                isEdited = userDao.edit(login, name, surname);
            } catch (DaoException exception) {
                logger.log(Level.ERROR, exception);
                throw new ServiceException(exception);
            }
        } else {
            throw new InvalidParamsException();
        }
        return isEdited;
    }

    @Override
    public List<User> findByPartOfLogin(String part) throws ServiceException {
        List<User> users;
        StringBuilder pattern = new StringBuilder("%");
        pattern.append(part).append("%");
        try {
            users = userDao.findByPattern(pattern.toString());
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return users;
    }

    @Override
    public List<User> findAllNotAdmins() throws ServiceException {
        List<User> users;
        try {
            users = userDao.findAllNotAdmins();
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return users;
    }

    @Override
    public boolean updateAvatarImagePath(String imagePath, long userId) throws ServiceException {
        boolean isEdited;
        try {
            isEdited = userDao.editImagePath(imagePath, userId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return isEdited;
    }

    @Override
    public boolean changeRole(String userRole, long userId) throws ServiceException {
        boolean isEdited;
        try {
            isEdited = userDao.changeRole(userRole, userId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception);
            throw new ServiceException(exception);
        }
        return isEdited;
    }

    @Override
    public Optional<User> findByLogin(String login) throws ServiceException {
        Optional<User> userOptional;
        if (LoginValidator.checkLogin(login)) {
            try {
                userOptional = userDao.findByLogin(login);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    if (user.getActivationCode() != null) {
                        throw new UserNotActivatedException();
                    }
                } else {
                    throw new InvalidParamsException();
                }
            } catch (DaoException exception) {
                logger.log(Level.ERROR, exception);
                throw new ServiceException(exception);
            }
        } else {
            throw new InvalidParamsException();
        }
        return userOptional;
    }
}
