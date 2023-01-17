package com.cruise.service.impl;

import com.cruise.dao.UserDAO;
import com.cruise.dto.UserDTO;
import com.cruise.exceptions.*;
import com.cruise.exceptions.constants.ExceptionMessage;
import com.cruise.model.User;
import com.cruise.service.UserService;
import com.cruise.utils.ConvertorUtil;
import com.cruise.utils.ValidationUtil;
import com.cruise.utils.constants.Regex;
import com.lambdaworks.crypto.SCryptUtil;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public User findById(int id) throws ServiceException {
        User user;
        try {
            user = userDAO.findById(id);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return user;
    }

    @Override
    public UserDTO findByLogin(String login) throws ServiceException{
        UserDTO userDTO;
        User user = userDAO.findByLogin(login);
        if (user == null){
            throw new UserNotRegisterException();
        }
        userDTO = ConvertorUtil.convertUserToDTO(user);
        return userDTO;
    }

    @Override
    public UserDTO signIn(String login, String password) throws ServiceException {
        UserDTO userDTO;
        User user = userDAO.findByLogin(login);
        if (user == null){
            throw new UserNotRegisterException();
        }
        if(!SCryptUtil.check(password, user.getPassword())){
            throw new PasswordDontMatchException();
        }
        userDTO = ConvertorUtil.convertUserToDTO(user);
        return userDTO;
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        List<User> users;
        try {
            users = userDAO.getAllUsers();
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return users;
    }

    @Override
    public void register(UserDTO userDTO, String password) throws ServiceException {
        validateUser(userDTO);
        User user = ConvertorUtil.convertUserDTOtoUser(userDTO);
        user.setPassword(SCryptUtil.scrypt(password, 16384, 8, 1));
        user.setRoleId(2);
        user.setBalance(0.0);
        try {
            userDAO.create(user);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void update(UserDTO userDTO) throws ServiceException {
        validateUser(userDTO);
        try {
            User user = ConvertorUtil.convertUserDTOtoUser(userDTO);
            userDAO.update(user);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public void remove(User user) throws ServiceException {
        try {
            userDAO.delete(user);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public void changePassword(User user, String password, String oldPassword) throws ServiceException {
        if(!SCryptUtil.check(oldPassword, user.getPassword())){
            throw new ServiceException("password dont match");
        }
        try {
            userDAO.changePassword(user, SCryptUtil.scrypt(password, 16384, 8, 1));
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void changeBalance(User user, double balance) throws ServiceException {
        try {
            userDAO.changeBalance(user, balance);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    private void validateUser(UserDTO userDTO) throws InvalidFormatException{
        User user = userDAO.findByLogin(userDTO.getLogin());
        if (user != null && !userDTO.getLogin().equals(user.getLogin())){
            throw new InvalidFormatException(ExceptionMessage.ERROR_LOGIN_IS_USED);
        }
        ValidationUtil.validateField(userDTO.getEmail(), Regex.EMAIL_REGEX, ExceptionMessage.ERROR_EMAIL);
        ValidationUtil.validateField(userDTO.getFirstName(), Regex.NAME_REGEX, ExceptionMessage.ERROR_FIRST_NAME);
        ValidationUtil.validateField(userDTO.getFirstName(), Regex.NAME_REGEX, ExceptionMessage.ERROR_LAST_NAME);
    }
}
