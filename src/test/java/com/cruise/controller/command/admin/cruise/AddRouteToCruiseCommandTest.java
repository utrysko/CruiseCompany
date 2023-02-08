package com.cruise.controller.command.admin.cruise;

import com.cruise.controller.AllPath;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.Route;
import com.cruise.model.service.CruiseService;
import com.cruise.model.service.RouteService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddRouteToCruiseCommandTest {

    static CruiseService mockCruiseService;
    static RouteService mockRouteService;
    static HttpServletRequest mockReq;
    static HttpServletResponse mockResp;
    static AddRouteToCruiseCommand addRouteToCruiseCommand;

    @BeforeAll
    static public void globalSetUp(){
        mockCruiseService = mock(CruiseService.class);
        mockRouteService = mock(RouteService.class);
        mockReq = mock(HttpServletRequest.class);
        mockResp = mock(HttpServletResponse.class);
        addRouteToCruiseCommand = new AddRouteToCruiseCommand(mockCruiseService, mockRouteService);
    }

    @BeforeEach
    public void setUp() throws ServiceException {
        when(mockReq.getParameter("routeId")).thenReturn("4");
        when(mockReq.getParameter("cruiseId")).thenReturn("6");
        when(mockCruiseService.findById(6)).thenReturn(new Cruise());
        when(mockRouteService.findById(4)).thenReturn(new Route());
        doNothing().when(mockCruiseService).addRouteToCruise(any(Route.class), any(Cruise.class));
    }

    @Test
    void execute() throws ServiceException{
        String path = addRouteToCruiseCommand.execute(mockReq, mockResp);

        assertEquals(AllPath.CRUISES_COMMAND, path);
        verify(mockRouteService, times(1)).findById(anyInt());
        verify(mockCruiseService, times(1)).findById(anyInt());
        verify(mockCruiseService, times(1)).addRouteToCruise(any(Route.class), any(Cruise.class));
    }
}