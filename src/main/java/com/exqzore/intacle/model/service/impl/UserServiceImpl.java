package com.exqzore.intacle.model.service.impl;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.exception.PasswordEncoderException;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.dao.UserDao;
import com.exqzore.intacle.model.dao.impl.UserDaoImpl;
import com.exqzore.intacle.model.entity.User;
import com.exqzore.intacle.model.service.UserService;
import com.exqzore.intacle.model.service.status.UserServiceStatus;
import com.exqzore.intacle.model.validator.Validator;
import com.exqzore.intacle.util.PasswordEncoder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private final static Logger logger = LogManager.getLogger();

    private static final UserServiceImpl instance = new UserServiceImpl();

    private final UserDao userDao = UserDaoImpl.getInstance();

    public static final String LOGIN = "login";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public UserServiceStatus register(Map<String, String> parameters) throws ServiceException {
        UserServiceStatus serviceStatus = new UserServiceStatus();
        boolean isLoginFree;
        try {
            isLoginFree = userDao.isLoginFree(parameters.get(LOGIN));
        } catch (DaoException exception) {
            logger.log(Level.ERROR, exception.getMessage());
            throw new ServiceException(exception);
        }
        boolean validationResult = Validator.checkRegistration(parameters);
        if (validationResult && isLoginFree) {
            try {
                User user = new User();
                user.setLogin(parameters.get(LOGIN));
                user.setEmail(parameters.get(EMAIL));
                user.setPassword(PasswordEncoder.encode(parameters.get(PASSWORD)));
                user.setName(parameters.get(NAME));
                user.setSurname(parameters.get(SURNAME));
                user.setActivationCode(UUID.randomUUID().toString());
                userDao.create(user);
                logger.log(Level.INFO, "New user: {}", user);
                serviceStatus.setUser(user);
            } catch (DaoException | PasswordEncoderException exception) {
                logger.log(Level.ERROR, exception.getMessage());
                throw new ServiceException(exception);
            }
        } else if (!validationResult) {
            serviceStatus.setInvalidParams(true);
        } else {
            serviceStatus.setLoginBusy(true);
        }
        return serviceStatus;
    }

    @Override
    public UserServiceStatus login(String login, String password) throws ServiceException {
        UserServiceStatus serviceStatus = new UserServiceStatus();
        boolean validationResult = Validator.checkLogIn(login, password);
        if (validationResult) {
            try {
                password = PasswordEncoder.encode(password);
                Optional<User> optionalUser = userDao.login(login, password);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    if (user.getActivationCode() == null) {
                        serviceStatus.setUser(user);
                    } else {
                        serviceStatus.setNotActivated(true);
                    }
                } else {
                    serviceStatus.setInvalidParams(true);
                }
            } catch (DaoException | PasswordEncoderException exception) {
                logger.log(Level.ERROR, exception.getMessage());
                throw new ServiceException(exception);
            }
        } else {
            serviceStatus.setInvalidParams(true);
        }
        return serviceStatus;
    }

    @Override
    public UserServiceStatus activate(String login, String activationCode) throws ServiceException {
        UserServiceStatus serviceStatus = new UserServiceStatus();
        boolean isActivated;
        boolean validationResult = Validator.checkActivateUser(login, activationCode);
        if (validationResult) {
            try {
                isActivated = userDao.activate(login, activationCode);
                if (!isActivated) {
                    serviceStatus.setInvalidParams(true);
                }
            } catch (DaoException exception) {
                logger.log(Level.ERROR, exception.getMessage());
                throw new ServiceException(exception);
            }
        } else {
            serviceStatus.setInvalidParams(true);
        }
        return serviceStatus;
    }
}
