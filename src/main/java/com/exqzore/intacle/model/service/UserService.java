package com.exqzore.intacle.model.service;

import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.service.status.UserServiceStatus;

import java.util.Map;

public interface UserService {
    UserServiceStatus register(Map<String, String> parameters) throws ServiceException;

    UserServiceStatus login(String login, String password) throws ServiceException;

    UserServiceStatus activate(String login, String activationCode) throws ServiceException;
}
