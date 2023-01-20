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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public User findById(int id) throws ServiceException {
        User user;
        ValidationUtil.validateAllDigitCruiseFields(id);
        try {
            user = userDAO.findById(id);
        } catch (DAOException e){
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return user;
    }

    @Override
    public User findByLogin(String login) throws ServiceException{
        User user;
        try {
            user = userDAO.findByLogin(login);
        } catch (DAOException e){
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        if (user == null){
            throw new UserNotRegisterException();
        }
        return user;
    }

    @Override
    public UserDTO signIn(String login, String password) throws ServiceException {
        UserDTO userDTO;
        User user = findByLogin(login);
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
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return users;
    }

    @Override
    public int countAll() throws ServiceException {
        int amount;
        try {
            amount = userDAO.countAll();
        } catch (DAOException e){
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return amount;
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
            LOG.error(e.getMessage());
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
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public void remove(User user) throws ServiceException {
        try {
            userDAO.delete(user);
        } catch (DAOException e){
            LOG.error(e.getMessage());
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
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void changeBalance(User user, double balance) throws ServiceException {
        try {
            userDAO.changeBalance(user, balance);
        } catch (DAOException e){
            LOG.error(e.getMessage());
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
