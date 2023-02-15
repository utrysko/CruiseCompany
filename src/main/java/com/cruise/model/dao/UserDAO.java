package com.cruise.model.dao;

import com.cruise.exceptions.DAOException;
import com.cruise.model.entities.User;

import java.util.List;
import java.util.Optional;

/**
 * User DAO interface.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public interface UserDAO {

    /**
     * Obtains user instance from database
     *
     * @param id - user id
     * @return User
     */
    Optional<User> findById(int id) throws DAOException;

    /**
     * Obtains user instance from database
     *
     * @param login - user login
     * @return User
     */
    Optional<User> findByLogin(String login) throws DAOException;

    /**
     * Obtains all users instances from database
     *
     * @return List of users
     */
    List<User> getAllUsers() throws DAOException;

    /**
     * Obtains number of users records in database
     *
     * @return int - number of records
     */
    int countAll() throws DAOException;

    /**
     * Insert user in database
     *
     * @param user - instance of User
     */
    void create(User user) throws DAOException;

    /**
     * Update user in database
     *
     * @param user - instance of User
     */
    void update(User user) throws DAOException;

    /**
     * Delete user from database
     *
     * @param user - instance of User
     */
    void delete(User user) throws DAOException;

    /**
     * Change user password in database
     *
     * @param user - instance of User
     * @param password - new password
     */
    void changePassword(User user, String password) throws DAOException;

    /**
     * Change user balance in database
     *
     * @param user - instance of User
     * @param balance - new balance
     */
    void changeBalance(User user, double balance) throws DAOException;
}
