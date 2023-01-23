package com.cruise.service.impl;

import com.cruise.dao.CruiseDAO;
import com.cruise.dao.RouteDAO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Route;
import com.cruise.service.RouteService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RouteServiceImplTest {
    static RouteDAO mockRouteDAO;
    static CruiseDAO mockCruiseDAO;
    static RouteService routeService;
    static Route testRoute;
    static int routeId;

    @BeforeAll
    static public void globalSetUp() {
        routeId = 7;
        testRoute = new Route();
        testRoute.setStartPort("Genoa");
        testRoute.setMiddlePorts(List.of("Marseille"));
        testRoute.setEndPort("Monaco");
        mockRouteDAO = mock(RouteDAO.class);
        mockCruiseDAO = mock(CruiseDAO.class);
        routeService = new RouteServiceImpl(mockRouteDAO, mockCruiseDAO);
    }
    @BeforeEach
    public void setUp() {
        reset(mockCruiseDAO);
        reset(mockRouteDAO);
        when(mockRouteDAO.findById(routeId)).thenReturn(testRoute);

    }

    @Test
    void findById() throws ServiceException {
        Route route = routeService.findById(routeId);

        verify(mockRouteDAO, times(1)).findById(anyInt());
        assertEquals(testRoute.getStartPort(), route.getStartPort());
        assertThrows(ServiceException.class, () -> routeService.findById(0));
    }

    @Test
    void getAllRoutes() {
    }

    @Test
    void countAll() {
    }

    @Test
    void getRoutesInOrderAndLimit() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}