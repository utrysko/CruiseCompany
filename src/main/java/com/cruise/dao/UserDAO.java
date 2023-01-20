package com.cruise.dao;

import com.cruise.exceptions.DAOException;
import com.cruise.model.User;

import java.util.List;

public interface UserDAO {

    User findById(int id) throws DAOException;
    User findByLogin(String login) throws DAOException;
    List<User> getAllUsers() throws DAOException;
    int countAll() throws DAOException;
    void create(User user) throws DAOException;
    void update(User user) throws DAOException;
    void delete(User user) throws DAOException;
    void changePassword(User user, String password) throws DAOException;
    void changeBalance(User user, double balance) throws DAOException;
}
