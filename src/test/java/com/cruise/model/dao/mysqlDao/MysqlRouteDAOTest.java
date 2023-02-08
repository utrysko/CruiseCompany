package com.cruise.model.dao.mysqlDao;

import com.cruise.model.connection.DataSource;
import com.cruise.model.dao.RouteDAO;
import com.cruise.model.entities.Route;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class MysqlRouteDAOTest {

    static RouteDAO routeDAO;
    static DataSource mockDataSource;
    static Connection mockConn;
    static PreparedStatement mockPreparedStmt;
    static ResultSet mockResultSet;
    static Route testRoute;
    static int routeId;

    @BeforeAll
    static public void globalSetUp() throws SQLException {
        mockDataSource = mock(DataSource.class);
        mockConn = mock(Connection.class);
        mockPreparedStmt = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        routeDAO = new MysqlRouteDAO(mockDataSource);
        routeId = 8;
        testRoute = new Route();
        testRoute.setNumberOfPorts(3);
        testRoute.setStartPort("Naples");
        testRoute.setMiddlePorts(List.of("Palermo"));
        testRoute.setEndPort("Monaco");
    }

    @BeforeEach
    public void setUp() throws SQLException {
        reset(mockPreparedStmt);
        reset(mockConn);
        reset(mockResultSet);

        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStmt);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStmt);
        doNothing().when(mockPreparedStmt).setInt(anyInt(), anyInt());
        doNothing().when(mockPreparedStmt).setString(anyInt(), anyString());
        when(mockPreparedStmt.executeUpdate()).thenReturn(1);
        when(mockPreparedStmt.executeQuery()).thenReturn(mockResultSet);
        when(mockPreparedStmt.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(1)).thenReturn(routeId);
        when(mockResultSet.getInt("id")).thenReturn(routeId);
        when(mockResultSet.getInt("number_of_ports")).thenReturn(testRoute.getNumberOfPorts());
        when(mockResultSet.getString("start_port")).thenReturn(testRoute.getStartPort());
        when(mockResultSet.getString("middle_ports")).thenReturn(testRoute.getMiddlePorts().get(0));
        when(mockResultSet.getString("end_port")).thenReturn(testRoute.getEndPort());
        when(mockResultSet.getInt("total")).thenReturn(1);
    }

    @Test
    void findById() throws SQLException{
        Route route = routeDAO.findById(routeId);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertEquals(routeId, route.getId());
        assertEquals(testRoute.getStartPort(), route.getStartPort());
    }

    @Test
    void getAllRoutes() throws SQLException{
        List<Route> routes = routeDAO.getAllRoutes();
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(2)).next();

        assertEquals(routeId, routes.get(0).getId());
        assertEquals(testRoute.getStartPort(), routes.get(0).getStartPort());
    }

    @Test
    void countAll() throws SQLException{
        int amount = routeDAO.countAll();
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertEquals(1, amount);
    }

    @Test
    void getRoutesInOrderAndLimit() throws SQLException{
        List<Route> routes = routeDAO.getRoutesInOrderAndLimit(1, 1, 1);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(3)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeQuery();
        verify(mockResultSet, times(2)).next();

        assertEquals(routeId, routes.get(0).getId());
        assertEquals(testRoute.getStartPort(), routes.get(0).getStartPort());
    }

    @Test
    void create() throws SQLException{
        Route route = testRoute;
        routeDAO.create(route);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(3)).setString(anyInt(), anyString());
        verify(mockPreparedStmt, times(1)).executeUpdate();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getInt(1);

        assertEquals(routeId, route.getId());
    }

    @Test
    void update() throws SQLException{
        Route route = testRoute;
        route.setId(routeId);
        route.setStartPort("Genoa");
        routeDAO.update(route);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(2)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(3)).setString(anyInt(), anyString());
        verify(mockPreparedStmt, times(1)).executeUpdate();
    }

    @Test
    void delete() throws SQLException{
        Route route = testRoute;
        route.setId(routeId);
        routeDAO.delete(route);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmt, times(1)).executeUpdate();

        assertEquals(0, route.getId());
    }
}