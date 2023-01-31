package com.cruise.dao.mysqlDao;

import com.cruise.model.dao.CruiseDAO;
import com.cruise.model.dao.TicketDAO;
import com.cruise.model.dao.mysqlDao.MysqlTicketDAO;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.Ticket;
import com.cruise.model.entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cruise.model.connection.DataSource;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MysqlTicketDAOTest {
    static TicketDAO ticketDAO;
    static CruiseDAO mockCruiseDAO;
    static DataSource mockDataSource;
    static Connection mockConn;
    static PreparedStatement mockPreparedStmt;
    static ResultSet mockResultSet;
    static Blob mockBlob;
    static Ticket testTicket;
    static int ticketId;
    static Cruise testCruise;
    static User testUser;

    @BeforeAll
    static public void globalSetUp() {
        testCruise = new Cruise();
        testCruise.setId(9);
        testUser = new User();
        testUser.setId(2);
        mockCruiseDAO = mock(CruiseDAO.class);
        mockDataSource = mock(DataSource.class);
        mockConn = mock(Connection.class);
        mockPreparedStmt = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        mockBlob = mock(Blob.class);
        ticketDAO = new MysqlTicketDAO(mockDataSource, mockCruiseDAO);
        ticketId = 4;
        testTicket = new Ticket();
        testTicket.setClientId(testUser.getId());
        testTicket.setCruiseId(testCruise.getId());
        testTicket.setCruise(new Cruise());
        testTicket.setStatus("Not Payed");
        testTicket.setDocument(mockBlob);
    }

    @BeforeEach
    public void setUp() throws SQLException {
        reset(mockPreparedStmt);
        reset(mockConn);
        reset(mockResultSet);

        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStmt);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStmt);
        when(mockCruiseDAO.findById(anyInt())).thenReturn(new Cruise());
        doNothing().when(mockPreparedStmt).setInt(anyInt(), anyInt());
        doNothing().when(mockPreparedStmt).setString(anyInt(), anyString());
        doNothing().when(mockPreparedStmt).setBlob(anyInt(), any(Blob.class));
        when(mockPreparedStmt.executeUpdate()).thenReturn(1);
        when(mockPreparedStmt.executeQuery()).thenReturn(mockResultSet);
        when(mockPreparedStmt.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(1)).thenReturn(ticketId);
        when(mockResultSet.getInt("id")).thenReturn(ticketId);
        when(mockResultSet.getInt("cruises_id")).thenReturn(testTicket.getCruiseId());
        when(mockResultSet.getInt("user_id")).thenReturn(testTicket.getClientId());
        when(mockResultSet.getString("status")).thenReturn(testTicket.getStatus());
        when(mockResultSet.getBlob("document")).thenReturn(testTicket.getDocument());
        when(mockResultSet.getInt("total")).thenReturn(1);
    }

    @Test
    void findAllByUser() throws SQLException{
        List<Ticket> tickets = ticketDAO.findAllByUser(testUser);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(2)).next();

        assertEquals(testTicket.getClientId(), tickets.get(0).getClientId());
        assertEquals(testTicket.getCruiseId(), tickets.get(0).getCruiseId());
    }

    @Test
    void findByAllByCruise() throws SQLException{
        List<Ticket> tickets = ticketDAO.findByAllByCruise(testCruise);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(2)).next();

        assertEquals(testTicket.getClientId(), tickets.get(0).getClientId());
        assertEquals(testTicket.getCruiseId(), tickets.get(0).getCruiseId());
    }

    @Test
    void findById() throws SQLException {
        Ticket ticket = ticketDAO.findById(ticketId);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertEquals(testTicket.getClientId(), ticket.getClientId());
        assertEquals(testTicket.getCruiseId(), ticket.getCruiseId());
    }

    @Test
    void findByUserAndCruiseShip() throws SQLException{
        Ticket ticket = ticketDAO.findByUserAndCruiseShip(testUser, testCruise);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(2)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertEquals(testTicket.getClientId(), ticket.getClientId());
        assertEquals(testTicket.getCruiseId(), ticket.getCruiseId());
    }

    @Test
    void getAllTickets() throws SQLException{
        List<Ticket> tickets = ticketDAO.getAllTickets();
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(2)).next();

        assertEquals(testTicket.getClientId(), tickets.get(0).getClientId());
        assertEquals(testTicket.getCruiseId(), tickets.get(0).getCruiseId());
    }

    @Test
    void countAll() throws SQLException{
        int amount = ticketDAO.countAll();
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertEquals(1, amount);
    }

    @Test
    void getTicketsInOrderAndLimit() throws SQLException{
        List<Ticket> tickets = ticketDAO.getTicketsInOrderAndLimit(1, 1, 1);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(3)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(2)).next();

        assertEquals(testTicket.getClientId(), tickets.get(0).getClientId());
        assertEquals(testTicket.getCruiseId(), tickets.get(0).getCruiseId());
    }

    @Test
    void create() throws SQLException{
        ticketDAO.create(testTicket, testUser, testCruise);
        //verify and assert
        verify(mockConn, times(1)).setAutoCommit(false);
        verify(mockConn, times(2)).prepareStatement(anyString());
        verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStmt, times(5)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).setDouble(anyInt(), anyDouble());
        verify(mockPreparedStmt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmt, times(1)).setBlob(anyInt(), any(Blob.class));
        verify(mockPreparedStmt, times(3)).executeUpdate();
        verify(mockConn, times(1)).commit();
        verify(mockConn, times(1)).close();
    }

    @Test
    void update() throws SQLException{
        testTicket.setId(ticketId);
        testTicket.setClientId(19);
        ticketDAO.update(testTicket);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(3)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmt, times(1)).executeUpdate();
    }

    @Test
    void delete() throws SQLException{
        ticketDAO.delete(testTicket);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeUpdate();
    }

    @Test
    void changeStatus() throws SQLException{
        testTicket.setId(ticketId);
        ticketDAO.changeStatus(testTicket, "Status");
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmt, times(1)).executeUpdate();
    }
}