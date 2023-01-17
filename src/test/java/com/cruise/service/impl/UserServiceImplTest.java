package com.cruise.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cruise.connection.DataSource;
import com.cruise.dao.UserDAO;
import com.cruise.dao.mysqlDao.MysqlUserDAO;
import com.cruise.dto.UserDTO;
import com.cruise.exceptions.DAOException;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.User;
import com.cruise.service.UserService;
import com.lambdaworks.crypto.SCryptUtil;
import org.junit.jupiter.api.*;


import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    static UserDAO mockUserDAO;
    static UserService userService;
    static User testUser;
    static int userId;
    static String password;

    @BeforeAll
    static public void globalSetUp(){
        password = "12345";
        testUser = new User();
        testUser.setLogin("login");
        testUser.setFirstName("First");
        testUser.setLastName("Last");
        testUser.setEmail("email@email.com");
        testUser.setPassword((SCryptUtil.scrypt(password, 16384, 8, 1)));
        testUser.setRoleId(2);
        testUser.setBalance(10.0);
        userId = 11;
        mockUserDAO = mock(UserDAO.class);
        userService = new UserServiceImpl(mockUserDAO);
    }

    @BeforeEach
    public void setUp() {
        reset(mockUserDAO);
        when(mockUserDAO.findById(userId)).thenReturn(testUser);
        when(mockUserDAO.findByLogin(testUser.getLogin())).thenReturn(testUser);
        when(mockUserDAO.getAllUsers()).thenReturn(List.of(testUser));

    }

    @Test
    void findById() throws ServiceException{
        User user = userService.findById(userId);

        assertEquals(testUser.getLogin(), user.getLogin());
        assertEquals(testUser.getEmail(), user.getEmail());
    }

    @Test
    void findByLogin() throws ServiceException{
        User user = userService.findByLogin(testUser.getLogin());

        assertEquals(testUser.getFirstName(), user.getFirstName());
        assertEquals(testUser.getEmail(), user.getEmail());
    }

    @Test
    void signIn() throws ServiceException{
        UserDTO userDTO = userService.signIn(testUser.getLogin(), password);

        assertEquals(testUser.getFirstName(), userDTO.getFirstName());
        assertEquals(testUser.getEmail(), userDTO.getEmail());
    }

    @Test
    void findAllUsers() throws ServiceException{
        List<User> users = userService.findAllUsers();

        assertEquals(testUser.getLogin(), users.get(0).getLogin());
        assertEquals(testUser.getEmail(), users.get(0).getEmail());
    }

    @Test
    void register() {
    }

    @Test
    void update() {
    }

    @Test
    void remove() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void changeBalance() {
    }
}