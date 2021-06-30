package com.exqzore.intacle.service;

import com.exqzore.intacle.entity.User;
import com.exqzore.intacle.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Register user.
     *
     * @param login the user login
     * @param email the user email
     * @param password the user password
     * @param repeatPassword the same password to check the correctness of the input
     * @param name the user name
     * @param surname the user surname
     * @return The registered user
     * @throws ServiceException the service exception
     */
    Optional<User> register(String login, String email, String password, String repeatPassword, String name,
                            String surname) throws ServiceException;

    /**
     * Login user.
     *
     * @param login the user login
     * @param password the user password
     * @return The authenticated user
     * @throws ServiceException the service exception
     */
    Optional<User> login(String login, String password) throws ServiceException;

    /**
     * Find by user login.
     *
     * @param login the user login
     * @return The founded user
     * @throws ServiceException the service exception
     */
    Optional<User> findByLogin(String login) throws ServiceException;

    /**
     * Activate user.
     *
     * @param login the user login
     * @param activationCode the user activation code
     * @return True in case of user activation and false otherwise
     * @throws ServiceException the service exception
     */
    boolean activate(String login, String activationCode) throws ServiceException;

    /**
     * Edit user.
     *
     * @param login the user login
     * @param name the user name
     * @param surname the user surname
     * @return True in case of user change and false otherwise
     * @throws ServiceException the service exception
     */
    boolean edit(String login, String name, String surname) throws ServiceException;

    /**
     * Find users by part of login.
     *
     * @param part the part of user login
     * @return A list of users
     * @throws ServiceException the service exception
     */
    List<User> findByPartOfLogin(String part) throws ServiceException;

    /**
     * Find all users who are not admins.
     *
     * @return A list of users who are not admins
     * @throws ServiceException the service exception
     */
    List<User> findAllNotAdmins() throws ServiceException;

    /**
     * Update user avatar image path.
     *
     * @param imagePath the new image path
     * @param userId the user id
     * @return True in case of path update and false otherwise
     * @throws ServiceException the service exception
     */
    boolean updateAvatarImagePath(String imagePath, long userId) throws ServiceException;

    /**
     * Change user role.
     *
     * @param userRole the new user role
     * @param userId the user id
     * @return True in case of user role change and false otherwise
     * @throws ServiceException the service exception
     */
    boolean changeRole(String userRole, long userId) throws ServiceException;
}
