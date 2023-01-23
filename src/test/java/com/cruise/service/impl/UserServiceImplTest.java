package com.cruise.service.impl;

import com.cruise.dao.UserDAO;
import com.cruise.dto.UserDTO;
import com.cruise.exceptions.InvalidFormatException;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.User;
import com.cruise.service.UserService;
import com.lambdaworks.crypto.SCryptUtil;
import org.junit.jupiter.api.*;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        when(mockUserDAO.countAll()).thenReturn(1);
        doNothing().when(mockUserDAO).create(any(User.class));
        doNothing().when(mockUserDAO).update(any(User.class));
        doNothing().when(mockUserDAO).delete(any(User.class));
        doNothing().when(mockUserDAO).changePassword(any(User.class), anyString());
        doNothing().when(mockUserDAO).changeBalance(any(User.class), anyDouble());

    }

    @Test
    void findById() throws ServiceException{
        User user = userService.findById(userId);

        verify(mockUserDAO, times(1)).findById(anyInt());
        assertEquals(testUser.getLogin(), user.getLogin());
        assertEquals(testUser.getEmail(), user.getEmail());
        assertThrows(InvalidFormatException.class, () -> userService.findById(-1));
    }

    @Test
    void findByLogin() throws ServiceException{
        User user = userService.findByLogin(testUser.getLogin());

        verify(mockUserDAO, times(1)).findByLogin(anyString());
        assertEquals(testUser.getFirstName(), user.getFirstName());
        assertEquals(testUser.getEmail(), user.getEmail());
    }

    @Test
    void signIn() throws ServiceException{
        UserDTO userDTO = userService.signIn(testUser.getLogin(), password);

        verify(mockUserDAO, times(1)).findByLogin(anyString());
        assertEquals(testUser.getFirstName(), userDTO.getFirstName());
        assertEquals(testUser.getEmail(), userDTO.getEmail());
    }

    @Test
    void findAllUsers() throws ServiceException{
        List<User> users = userService.findAllUsers();

        verify(mockUserDAO, times(1)).getAllUsers();
        assertEquals(testUser.getLogin(), users.get(0).getLogin());
        assertEquals(testUser.getEmail(), users.get(0).getEmail());
    }

    @Test
    void countAll() throws ServiceException{
        int amount = userService.countAll();

        verify(mockUserDAO, times(1)).countAll();
        assertEquals(1, amount);
    }
    @Test
    void register() throws ServiceException{
        UserDTO testUserDTO;
        testUserDTO = new UserDTO();
        testUserDTO.setLogin("edward");
        testUserDTO.setFirstName("Edward");
        testUserDTO.setLastName("Smith");
        testUserDTO.setEmail("edwardSmith@gmail.com");
        userService.register(testUserDTO, password);

        verify(mockUserDAO, times(1)).create(any(User.class));
    }

    @Test
    void update() throws ServiceException{
        UserDTO testUserDTO = new UserDTO();
        testUserDTO.setId(15);
        testUserDTO.setLogin("edward");
        testUserDTO.setFirstName("Edward");
        testUserDTO.setLastName("Smith");
        testUserDTO.setEmail("email@email.com");
        testUserDTO.setRoleId(2);
        testUserDTO.setBalance(0.0);
        userService.update(testUserDTO);

        verify(mockUserDAO, times(1)).update(any(User.class));
    }

    @Test
    void remove() throws ServiceException{
        userService.remove(testUser);

        verify(mockUserDAO, times(1)).delete(any(User.class));
    }

    @Test
    void changePassword() throws ServiceException{
        userService.changePassword(testUser, "123456", password);

        verify(mockUserDAO).changePassword(any(User.class), anyString());
    }

    @Test
    void changeBalance() throws ServiceException {
        userService.changeBalance(testUser, 15.0);

        verify(mockUserDAO).changeBalance(any(User.class), anyDouble());
    }
}