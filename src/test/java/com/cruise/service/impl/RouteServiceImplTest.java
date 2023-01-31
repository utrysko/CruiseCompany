package com.cruise.service.impl;

import com.cruise.model.dao.CruiseDAO;
import com.cruise.model.dao.RouteDAO;
import com.cruise.dto.RouteDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Route;
import com.cruise.model.service.RouteService;
import com.cruise.model.service.impl.RouteServiceImpl;
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
        when(mockRouteDAO.getAllRoutes()).thenReturn(List.of(testRoute));
        when(mockRouteDAO.countAll()).thenReturn(1);
        when(mockRouteDAO.getRoutesInOrderAndLimit(anyInt(), anyInt(), anyInt())).thenReturn(List.of(testRoute));
        doNothing().when(mockRouteDAO).create(any(Route.class));
        doNothing().when(mockRouteDAO).update(any(Route.class));
        doNothing().when(mockRouteDAO).delete(any(Route.class));
        when(mockCruiseDAO.cruiseUsedRoute(anyInt())).thenReturn(false);

    }

    @Test
    void findById() throws ServiceException {
        Route route = routeService.findById(routeId);

        verify(mockRouteDAO, times(1)).findById(anyInt());
        assertEquals(testRoute.getStartPort(), route.getStartPort());
        assertThrows(ServiceException.class, () -> routeService.findById(0));
    }

    @Test
    void getAllRoutes() throws ServiceException{
        List<Route> routes = routeService.getAllRoutes();

        verify(mockRouteDAO, times(1)).getAllRoutes();
        assertEquals(testRoute.getStartPort(), routes.get(0).getStartPort());
        assertEquals(1, routes.size());
    }

    @Test
    void countAll() throws ServiceException{
        int amount = routeService.countAll();

        verify(mockRouteDAO, times(1)).countAll();
        assertEquals(1, amount);
    }

    @Test
    void getRoutesInOrderAndLimit() throws ServiceException{
        List<Route> routes = routeService.getRoutesInOrderAndLimit(1, 1, 1);

        verify(mockRouteDAO, times(1)).getRoutesInOrderAndLimit(1, 1, 1);
        assertEquals(testRoute.getStartPort(), routes.get(0).getStartPort());
        assertEquals(1, routes.size());
    }

    @Test
    void create() throws ServiceException{
        RouteDTO routeDTO = new RouteDTO();
        routeDTO.setStartPort("Naples");
        routeDTO.setMiddlePorts(List.of("Valencia"));
        routeDTO.setEndPort("Marseille");

        routeService.create(routeDTO);
        verify(mockRouteDAO, times(1)).create(any(Route.class));
        routeDTO.setStartPort("sdasdsa");
        assertThrows(ServiceException.class, () -> routeService.create(routeDTO));
    }

    @Test
    void update() throws ServiceException{
        RouteDTO routeDTO = new RouteDTO();
        routeDTO.setId(3);
        routeDTO.setStartPort("Valencia");
        routeDTO.setMiddlePorts(List.of("Naples"));
        routeDTO.setEndPort("Marseille");

        routeService.update(routeDTO);
        verify(mockRouteDAO, times(1)).update(any(Route.class));
        routeDTO.setStartPort("sdasdsa");
        assertThrows(ServiceException.class, () -> routeService.create(routeDTO));
    }

    @Test
    void delete() throws ServiceException{
        routeService.delete(routeId);

        verify(mockCruiseDAO, times(1)).cruiseUsedRoute(anyInt());
        verify(mockRouteDAO, times(1)).findById(anyInt());
        verify(mockRouteDAO, times(1)).delete(any(Route.class));
    }
}