package com.cruise.dao.mysqlDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cruise.connection.DataSource;
import com.cruise.dao.CruiseDAO;
import com.cruise.dao.CruiseShipDAO;
import com.cruise.dao.RouteDAO;
import com.cruise.model.Cruise;
import com.cruise.model.CruiseShip;
import com.cruise.model.Route;
import org.junit.jupiter.api.*;


import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MysqlCruiseDAOTest {

    static CruiseDAO cruiseDAO;
    static CruiseShipDAO mockCruiseShipDAO;
    static RouteDAO mockRouteDAO;

    static DataSource mockDataSource;

    static Connection mockConn;

    static PreparedStatement mockPreparedStmt;

    static ResultSet mockResultSet;
    static Cruise testCruise;
    static int cruiseId;

    @BeforeAll
    static public void globalSetUp() throws SQLException{
        mockRouteDAO = mock(RouteDAO.class);
        mockCruiseShipDAO = mock(CruiseShipDAO.class);
        mockDataSource = mock(DataSource.class);
        mockConn = mock(Connection.class);
        mockPreparedStmt = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        cruiseDAO = new MysqlCruiseDAO(mockDataSource, mockCruiseShipDAO, mockRouteDAO);
        cruiseId = 15;
        testCruise = new Cruise();
        testCruise.setStart(Date.valueOf(LocalDate.now()));
        testCruise.setEnd(Date.valueOf(LocalDate.now().plusDays(5)));
        testCruise.setStatus("Status");
        testCruise.setTicketPrice(50.0);
        testCruise.setFreeSpaces(50);

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
        doNothing().when(mockPreparedStmt).setDate(anyInt(), any());
        doNothing().when(mockPreparedStmt).setDouble(anyInt(), anyDouble());
        doNothing().when(mockPreparedStmt).setInt(anyInt(), anyInt());
        when(mockCruiseShipDAO.findById(anyInt())).thenReturn(new CruiseShip());
        when(mockRouteDAO.findById(anyInt())).thenReturn(new Route());
        when(mockPreparedStmt.executeUpdate()).thenReturn(1);
        when(mockPreparedStmt.executeQuery()).thenReturn(mockResultSet);
        when(mockPreparedStmt.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(1)).thenReturn(cruiseId);
        when(mockResultSet.getInt("id")).thenReturn(cruiseId);
        when(mockResultSet.getDate("start")).thenReturn(testCruise.getStart());
        when(mockResultSet.getDate("end")).thenReturn(testCruise.getEnd());
        when(mockResultSet.getString("status")).thenReturn(testCruise.getStatus());
        when(mockResultSet.getDouble("ticket_price")).thenReturn(testCruise.getTicketPrice());
        when(mockResultSet.getInt("free_spaces")).thenReturn(testCruise.getFreeSpaces());
    }

    @Test
    void findById() throws SQLException {
        Cruise cruise = cruiseDAO.findById(cruiseId);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertEquals(cruise.getId(), cruiseId);
        assertEquals(cruise.getStatus(), testCruise.getStatus());
    }

    @Test
    void create() throws SQLException {
        Cruise cruise = testCruise;
        cruiseDAO.create(cruise);

        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStmt, times(2)).setDate(anyInt(), any());
        verify(mockPreparedStmt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmt, times(1)).setDouble(anyInt(), anyDouble());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeUpdate();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getInt(1);

        assertEquals(cruise.getId(), cruiseId);
    }

    @Test
    void getAllCruise() throws SQLException {
        List<Cruise> list = cruiseDAO.getAllCruise();

        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(2)).next();

        assertEquals(list.size(), 1);
        assertEquals(list.get(0).getId(), cruiseId);
    }

    @Test
    void getCruisesInOrderAndLimit() throws SQLException {
        List<Cruise> list = cruiseDAO.getCruisesInOrderAndLimit(1, 1, 1);

        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(3)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(2)).next();

        assertEquals(list.size(), 1);
        assertEquals(list.get(0).getId(), cruiseId);
    }

    @Test
    void update() throws SQLException {
        Cruise cruise = testCruise;
        cruise.setId(cruiseId);
        cruise.setStart(Date.valueOf(LocalDate.now().plusDays(2)));
        cruise.setEnd(Date.valueOf(LocalDate.now().plusDays(6)));
        cruise.setTicketPrice(50.5);
        cruiseDAO.update(cruise);

        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(2)).setDate(anyInt(), any());
        verify(mockPreparedStmt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmt, times(1)).setDouble(anyInt(), anyDouble());
        verify(mockPreparedStmt, times(2)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeUpdate();

    }

    @Test
    void delete() throws SQLException{
        Cruise cruise = testCruise;
        cruise.setId(cruiseId);
        cruiseDAO.delete(cruise);

        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeUpdate();

        assertEquals(cruise.getId(), 0);
    }

    @Test
    void changeStatus() throws SQLException{
        Cruise cruise = testCruise;
        cruise.setId(cruiseId);
        String status = "New Status";
        cruiseDAO.changeStatus(cruise, status);

        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeUpdate();

        assertEquals(cruise.getStatus(), status);
    }

    @Test
    void addShipToCruise() throws SQLException{
        Cruise cruise = testCruise;
        cruise.setId(cruiseId);
        CruiseShip cruiseShip = new CruiseShip();
        cruiseShip.setId(8);
        cruiseDAO.addShipToCruise(cruiseShip, cruise);

        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(2)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeUpdate();

        assertEquals(cruise.getCruiseShip(), cruiseShip);
    }

    @Test
    void addRouteToCruise() throws SQLException{
        Cruise cruise = testCruise;
        cruise.setId(cruiseId);
        Route route = new Route();
        route.setId(7);
        cruiseDAO.addRouteToCruise(route, cruise);

        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(2)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeUpdate();

        assertEquals(cruise.getRoute(), route);
    }
}