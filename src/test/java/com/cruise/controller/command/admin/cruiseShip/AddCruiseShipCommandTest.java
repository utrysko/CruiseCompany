package com.cruise.controller.command.admin.cruiseShip;

import com.cruise.controller.AllPath;
import com.cruise.dto.CruiseShipDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.service.CruiseShipService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class AddCruiseShipCommandTest {

    static HttpServletRequest mockReq;
    static HttpServletResponse mockResp;
    static CruiseShipService mockCruiseShipService;
    static AddCruiseShipCommand addCruiseShipCommand;

    @BeforeAll
    static public void globalSetUp(){
        mockCruiseShipService = mock(CruiseShipService.class);
        mockReq = mock(HttpServletRequest.class);
        mockResp = mock(HttpServletResponse.class);
        addCruiseShipCommand = new AddCruiseShipCommand(mockCruiseShipService);
    }

    @BeforeEach
    public void setUp() throws ServiceException {
        reset(mockCruiseShipService);
        reset(mockReq);
        reset(mockResp);
        doNothing().when(mockCruiseShipService).create(any(CruiseShipDTO.class));
        when(mockReq.getParameter("name")).thenReturn("Naomi");
        when(mockReq.getParameter("capacity")).thenReturn("50");
        when(mockReq.getParameter("status")).thenReturn("Available");
    }

    @Test
    void execute() throws ServiceException {
        String path = addCruiseShipCommand.execute(mockReq, mockResp);
        //verify and assert
        assertEquals(AllPath.CRUISE_SHIPS_COMMAND, path);
        verify(mockCruiseShipService, times(1)).create(any(CruiseShipDTO.class));
        verify(mockReq, times(1)).getParameter("name");
        verify(mockReq, times(1)).getParameter("capacity");
        verify(mockReq, times(1)).getParameter("status");
    }
}