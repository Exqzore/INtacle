package com.exqzore.intacle.model.dao;

import com.exqzore.intacle.exception.DaoException;
import com.exqzore.intacle.model.entity.User;

import java.util.Optional;

public interface UserDao {
    void create(User user) throws DaoException;

    boolean activate(String login, String activationCode) throws DaoException;

    boolean isLoginFree(String login) throws DaoException;

    Optional<User> login(String login, String password) throws DaoException;
}
