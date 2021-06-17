package com.exqzore.intacle.dao;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    boolean create(User user) throws DaoException;

    boolean activate(String login, String activationCode) throws DaoException;

    boolean edit(String login, String name, String surname) throws DaoException;

    boolean isLoginFree(String login) throws DaoException;

    Optional<User> findByLoginAndPassword(String login, String password) throws DaoException;

    Optional<User> findByLogin(String login) throws DaoException;

    List<User> findByPattern(String pattern) throws DaoException;

    boolean editImagePath(String imagePath, long userId) throws DaoException;
}
