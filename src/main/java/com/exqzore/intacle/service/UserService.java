package com.exqzore.intacle.service;

import com.exqzore.intacle.entity.User;
import com.exqzore.intacle.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> register(String login, String email, String password, String repeatPassword, String name,
                            String surname) throws ServiceException;

    Optional<User> login(String login, String password) throws ServiceException;

    Optional<User> findByLogin(String login) throws ServiceException;

    boolean activate(String login, String activationCode) throws ServiceException;

    boolean edit(String login, String name, String surname) throws ServiceException;

    List<User> findByPartOfLogin(String part) throws ServiceException;

    boolean updateAvatarImagePath(String imagePath, long userId) throws ServiceException;
}
