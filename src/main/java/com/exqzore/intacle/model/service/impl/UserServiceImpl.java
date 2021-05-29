package com.exqzore.intacle.model.service.impl;

import com.exqzore.intacle.exception.*;
import com.exqzore.intacle.model.dao.UserDao;
import com.exqzore.intacle.model.dao.impl.UserDaoImpl;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.service.UserService;
import com.exqzore.intacle.model.validator.Validator;
import com.exqzore.intacle.util.PasswordEncoder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private final static Logger logger = LogManager.getLogger();

    private static final UserService instance = new UserServiceImpl();

    private final UserDao userDao = UserDaoImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        return instance;
    }

    @Override
    public Optional<User> register(String login, String email, String password, String repeatPassword, String name, String surname)
            throws ServiceException {
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
                user.setName(name.isEmpty() ? null : name);
                user.setSurname(surname.isEmpty() ? null : surname);
                user.setActivationCode(UUID.randomUUID().toString());
                if (userDao.create(user)) {
                    userOptional = Optional.of(user);
                } else {
                    userOptional = Optional.empty();
                }
            } catch (DaoException | PasswordEncoderException exception) {
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
                userOptional = userDao.login(login, password);
            } catch (DaoException | PasswordEncoderException exception) {
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
                logger.log(Level.ERROR, exception.getMessage());
                throw new ServiceException(exception);
            }
        } else {
            throw new InvalidParamsException();
        }
        return isActivated;
    }
}
