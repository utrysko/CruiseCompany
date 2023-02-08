package com.cruise.model.service;

import com.cruise.dto.UserDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.User;

import java.util.List;

/**
 * User service interface.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public interface UserService {

     /**
      * Calls DAO to get user instance from database
      *
      * @param id - user id
      * @return User
      */
     User findById(int id) throws ServiceException;

     /**
      * Calls DAO to get user instance from database
      *
      * @param login - user login
      * @return User
      */
     User findByLogin(String login) throws ServiceException;

     /**
      * Calls DAO to get user and verify with password
      *
      * @param login - user login
      * @param password - user password
      * @return UserDTO
      */
     UserDTO signIn(String login, String password) throws ServiceException;

     /**
      * Calls DAO to get all users instances from database
      *
      * @return List of users
      */
     List<User> findAllUsers() throws ServiceException;

     /**
      * Calls DAO to get number of users records in database
      *
      * @return number of records(int)
      */
     int countAll() throws ServiceException;

     /**
      * Calls DAO to insert user in database
      *
      * @param userDTO - instance of UserDTO
      * @param password - user password
      */
     void register(UserDTO userDTO, String password) throws ServiceException;

     /**
      * Calls DAO to update user in database
      *
      * @param userDTO - instance of UserDTO
      */
     void update(UserDTO userDTO) throws ServiceException;

     /**
      * Calls DAO to delete user from database
      *
      * @param user - instance of User
      */
     void remove(User user) throws ServiceException;

     /**
      * Calls DAO to change user password in database
      *
      * @param user - instance of User
      * @param oldPassword - old Password
      * @param password - new password
      */
     void changePassword(User user, String password, String oldPassword) throws ServiceException;

     /**
      * Calls DAO to change user balance in database
      *
      * @param user - instance of User
      * @param balance - new balance
      */
     void changeBalance(User user, double balance) throws ServiceException;
}
