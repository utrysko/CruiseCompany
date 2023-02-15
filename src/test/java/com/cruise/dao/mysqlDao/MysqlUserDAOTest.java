package com.cruise.dao.mysqlDao;

import com.cruise.model.connection.DataSource;
import com.cruise.model.dao.UserDAO;
import com.cruise.model.dao.mysqlDao.MysqlUserDAO;
import com.cruise.model.entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class MysqlUserDAOTest {

    static UserDAO userDAO;

    static DataSource mockDataSource;

    static Connection mockConn;

    static PreparedStatement mockPreparedStmt;

    static ResultSet mockResultSet;
    static User testUser;
    static int userId;

    @BeforeAll
    static public void globalSetUp() throws SQLException {
        mockDataSource = mock(DataSource.class);
        mockConn = mock(Connection.class);
        mockPreparedStmt = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        userDAO = new MysqlUserDAO(mockDataSource);
        userId = 6;
        testUser = new User();
        testUser.setLogin("login");
        testUser.setFirstName("First");
        testUser.setLastName("Last");
        testUser.setEmail("email@email.com");
        testUser.setPassword("password");
        testUser.setRoleId(2);
        testUser.setBalance(10.0);

    }

    @BeforeEach
    public void setUp() throws SQLException {
        reset(mockPreparedStmt);
        reset(mockConn);
        reset(mockResultSet);

        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStmt);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStmt);
        doNothing().when(mockPreparedStmt).setString(anyInt(), anyString());
        doNothing().when(mockPreparedStmt).setDouble(anyInt(), anyDouble());
        doNothing().when(mockPreparedStmt).setInt(anyInt(), anyInt());
        when(mockPreparedStmt.executeUpdate()).thenReturn(1);
        when(mockPreparedStmt.executeQuery()).thenReturn(mockResultSet);
        when(mockPreparedStmt.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(1)).thenReturn(userId);
        when(mockResultSet.getInt("id")).thenReturn(userId);
        when(mockResultSet.getString("login")).thenReturn(testUser.getLogin());
        when(mockResultSet.getString("first_name")).thenReturn(testUser.getFirstName());
        when(mockResultSet.getString("last_name")).thenReturn(testUser.getLastName());
        when(mockResultSet.getString("email")).thenReturn(testUser.getEmail());
        when(mockResultSet.getInt("role_id")).thenReturn(testUser.getRoleId());
        when(mockResultSet.getDouble("balance")).thenReturn(testUser.getBalance());

    }

    @Test
    void findById() throws SQLException{
        Optional<User> user = userDAO.findById(userId);

        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertEquals(userId, user.get().getId());
        assertEquals(testUser.getLogin(), user.get().getLogin());
    }

    @Test
    void findByLogin() throws SQLException{
        Optional<User> user = userDAO.findByLogin(testUser.getLogin());

        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertEquals(user.get().getId(), userId);
        assertEquals(testUser.getLogin(), user.get().getLogin());
    }

    @Test
    void getAllUsers() throws SQLException{
        List<User> users = userDAO.getAllUsers();

        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(2)).next();

        assertEquals(1, users.size());
        assertEquals(userId, users.get(0).getId());
    }

    @Test
    void create() throws SQLException{
        User user = testUser;
        userDAO.create(user);

        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStmt, times(5)).setString(anyInt(), anyString());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).setDouble(anyInt(), anyDouble());
        verify(mockPreparedStmt, times(1)).executeUpdate();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getInt(1);

        assertEquals(userId, user.getId());
    }

    @Test
    void update() throws SQLException{
        User user = testUser;
        user.setId(userId);
        user.setLogin("another");
        userDAO.update(user);

        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(4)).setString(anyInt(), anyString());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeUpdate();
    }

    @Test
    void delete() throws SQLException{
        User user = testUser;
        testUser.setId(userId);
        userDAO.delete(user);

        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmt, times(1)).executeUpdate();

        assertEquals(0, user.getId());
    }

    @Test
    void changePassword() throws SQLException{
        User user = testUser;
        user.setId(userId);
        String password = "another";
        userDAO.changePassword(user, password);

        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmt, times(1)).executeUpdate();

        assertEquals(password, user.getPassword());
    }

    @Test
    void changeBalance() throws SQLException{
        User user = testUser;
        user.setId(userId);
        double balance = 10.0;
        userDAO.changeBalance(user, balance);

        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).setDouble(anyInt(), anyDouble());
        verify(mockPreparedStmt, times(1)).executeUpdate();

        assertEquals(balance, user.getBalance());
    }
}