package com.exqzore.intacle.model.service;

import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.model.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> register(String login, String email, String password, String repeatPassword, String name, String surname) throws ServiceException;

    Optional<User> login(String login, String password) throws ServiceException;

    Optional<User> findByLogin(String login) throws ServiceException;

    boolean activate(String login, String activationCode) throws ServiceException;
}
