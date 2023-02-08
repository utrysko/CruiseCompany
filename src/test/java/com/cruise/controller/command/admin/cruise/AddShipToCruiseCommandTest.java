package com.cruise.controller.command.admin.cruise;

import com.cruise.controller.AllPath;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.CruiseShip;
import com.cruise.model.service.CruiseService;
import com.cruise.model.service.CruiseShipService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddShipToCruiseCommandTest {
    static CruiseService mockCruiseService;
    static CruiseShipService mockCruiseShipService;
    static HttpServletRequest mockReq;
    static HttpServletResponse mockResp;
    static AddShipToCruiseCommand addShipToCruiseCommand;

    @BeforeAll
    static public void globalSetUp(){
        mockCruiseService = mock(CruiseService.class);
        mockCruiseShipService = mock(CruiseShipService.class);
        mockReq = mock(HttpServletRequest.class);
        mockResp = mock(HttpServletResponse.class);
        addShipToCruiseCommand = new AddShipToCruiseCommand(mockCruiseService, mockCruiseShipService);
    }

    @BeforeEach
    public void setUp() throws ServiceException {
        when(mockReq.getParameter("cruiseShipId")).thenReturn("3");
        when(mockReq.getParameter("cruiseId")).thenReturn("5");
        when(mockCruiseService.findById(5)).thenReturn(new Cruise());
        when(mockCruiseShipService.findById(3)).thenReturn(new CruiseShip());
        doNothing().when(mockCruiseService).addShipToCruise(any(CruiseShip.class), any(Cruise.class));
    }

    @Test
    void execute() throws ServiceException {
        String path = addShipToCruiseCommand.execute(mockReq, mockResp);

        assertEquals(AllPath.CRUISES_COMMAND, path);
        verify(mockCruiseShipService, times(1)).findById(anyInt());
        verify(mockCruiseService, times(1)).findById(anyInt());
        verify(mockCruiseService, times(1)).addShipToCruise(any(CruiseShip.class), any(Cruise.class));
    }
}