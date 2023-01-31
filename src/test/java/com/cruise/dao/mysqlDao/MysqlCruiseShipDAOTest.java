package com.cruise.dao.mysqlDao;

import com.cruise.model.connection.DataSource;
import com.cruise.model.dao.CruiseShipDAO;
import com.cruise.model.dao.StaffDAO;
import com.cruise.model.dao.mysqlDao.MysqlCruiseShipDAO;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.CruiseShip;
import com.cruise.model.entities.Staff;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MysqlCruiseShipDAOTest {

    static CruiseShipDAO cruiseShipDAO;
    static StaffDAO mockStaffDAO;
    static DataSource mockDataSource;
    static Connection mockConn;
    static PreparedStatement mockPreparedStmt;
    static ResultSet mockResultSet;
    static CruiseShip testCruiseShip;
    static int cruiseShiId;

    @BeforeAll
    static public void globalSetUp() throws SQLException {
        cruiseShiId = 9;
        mockStaffDAO = mock(StaffDAO.class);
        mockDataSource = mock(DataSource.class);
        mockConn = mock(Connection.class);
        mockPreparedStmt = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        cruiseShipDAO = new MysqlCruiseShipDAO(mockDataSource, mockStaffDAO);
        testCruiseShip = new CruiseShip();
        testCruiseShip.setName("Ember");
        testCruiseShip.setCapacity(100);
        testCruiseShip.setStatus("Available");
        testCruiseShip.setAvailableFrom(Date.valueOf(LocalDate.now()));
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
        when(mockPreparedStmt.executeUpdate()).thenReturn(1);
        when(mockPreparedStmt.executeQuery()).thenReturn(mockResultSet);
        when(mockPreparedStmt.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(1)).thenReturn(cruiseShiId);
        when(mockResultSet.getInt("id")).thenReturn(cruiseShiId);
        when(mockResultSet.getString("name")).thenReturn(testCruiseShip.getName());
        when(mockResultSet.getInt("capacity")).thenReturn(testCruiseShip.getCapacity());
        when(mockResultSet.getString("status")).thenReturn(testCruiseShip.getStatus());
        when(mockResultSet.getDate("available_from")).thenReturn(testCruiseShip.getAvailableFrom());
        when(mockResultSet.getInt("total")).thenReturn(1);
        when(mockStaffDAO.getAllByCruiseId(anyInt())).thenReturn(List.of(new Staff()));
    }

    @Test
    void findById() throws SQLException{
        CruiseShip cruiseShip = cruiseShipDAO.findById(cruiseShiId);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertEquals(testCruiseShip.getName(), cruiseShip.getName());
    }

    @Test
    void findByName() throws SQLException{
        CruiseShip cruiseShip = cruiseShipDAO.findByName(testCruiseShip.getName());
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertEquals(testCruiseShip.getName(), cruiseShip.getName());
    }

    @Test
    void create() throws SQLException{
        CruiseShip cruiseShip = testCruiseShip;
        cruiseShipDAO.create(cruiseShip);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStmt, times(1)).setDate(anyInt(), any());
        verify(mockPreparedStmt, times(2)).setString(anyInt(), anyString());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeUpdate();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getInt(1);

        assertEquals(cruiseShiId, cruiseShip.getId());
    }

    @Test
    void getAllCruiseShip() throws SQLException{
        List<CruiseShip> cruiseShips = cruiseShipDAO.getAllCruiseShip();
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(2)).next();

        assertEquals(testCruiseShip.getName(), cruiseShips.get(0).getName());
    }

    @Test
    void countAll() throws SQLException{
        int amount = cruiseShipDAO.countAll();
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertEquals(1, amount);
    }

    @Test
    void getFreeCruiseShip() throws SQLException{
        Cruise cruise = new Cruise();
        cruise.setStart(Date.valueOf(LocalDate.now().plusDays(2)));
        List<CruiseShip> cruiseShips = cruiseShipDAO.getFreeCruiseShip(cruise);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setDate(anyInt(), any(Date.class));
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(2)).next();

        assertEquals(testCruiseShip.getName(), cruiseShips.get(0).getName());
    }

    @Test
    void getCruiseShipsInOrderAndLimit() throws SQLException {
        List<CruiseShip> cruiseShips = cruiseShipDAO.getCruiseShipsInOrderAndLimit(1, 1, 1);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(3)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(2)).next();

        assertEquals(testCruiseShip.getName(), cruiseShips.get(0).getName());
    }

    @Test
    void update() throws SQLException{
        testCruiseShip.setId(cruiseShiId);
        cruiseShipDAO.update(testCruiseShip);
        //verify
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(2)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmt, times(1)).executeUpdate();
    }

    @Test
    void delete() throws SQLException{
        cruiseShipDAO.delete(testCruiseShip);
        //verify
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmt, times(1)).executeUpdate();
    }

    @Test
    void changeStatus() throws SQLException{
        testCruiseShip.setId(cruiseShiId);
        cruiseShipDAO.changeStatus(testCruiseShip, "Status");
        //verify
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmt, times(1)).executeUpdate();
    }

    @Test
    void changeAvailableDate() throws SQLException{
        testCruiseShip.setId(cruiseShiId);
        cruiseShipDAO.changeAvailableDate(testCruiseShip, Date.valueOf(LocalDate.now().plusDays(3)));
        //verify
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).setDate(anyInt(), any(Date.class));
        verify(mockPreparedStmt, times(1)).executeUpdate();
    }
}