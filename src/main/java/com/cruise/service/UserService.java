package com.cruise.service;

import com.cruise.dto.UserDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.User;

import java.util.List;

/**
 * User service interface .
 *
 * @author Vasyl Utrysko.
 */
public interface UserService {
     User findById(int id) throws ServiceException;
     User findByLogin(String login) throws ServiceException;
     UserDTO signIn(String login, String password) throws ServiceException;
     List<User> findAllUsers() throws ServiceException;
     void register(UserDTO userDTO, String password) throws ServiceException;
     void update(UserDTO userDTO) throws ServiceException;
     void remove(User user) throws ServiceException;
     void changePassword(User user, String password, String oldPassword) throws ServiceException;
     void changeBalance(User user, double balance) throws ServiceException;
}
