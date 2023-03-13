package com.cruise.controller.command.admin.cruiseShip;

import com.cruise.controller.AllPath;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.CruiseShip;
import com.cruise.model.service.CruiseShipService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CruiseShipsCommandTest {

    static CruiseShipService mockCruiseShipService;
    static CruiseShipsCommand cruiseShipsCommand;
    static HttpServletRequest mockReq;
    static HttpServletResponse mockResp;
    static CruiseShip testCruiseShip;

    @BeforeAll
    static public void globalSetUp(){
        mockCruiseShipService = mock(CruiseShipService.class);
        mockReq = mock(HttpServletRequest.class);
        mockResp = mock(HttpServletResponse.class);
        cruiseShipsCommand = new CruiseShipsCommand(mockCruiseShipService);
        testCruiseShip = new CruiseShip();
        testCruiseShip.setId(5);
        testCruiseShip.setName("Ember");
        testCruiseShip.setStatus("Available");
        testCruiseShip.setCapacity(50);
    }

    @BeforeEach
    public void setUp() throws ServiceException {
        reset(mockCruiseShipService);
        reset(mockReq);
        reset(mockResp);
        when(mockCruiseShipService.getCruiseShipsInOrderAndLimit(anyInt(), anyInt(), anyInt())).thenReturn(List.of(testCruiseShip));
        when(mockCruiseShipService.countAll()).thenReturn(1);
        doNothing().when(mockReq).setAttribute(anyString(), any());
    }

    @Test
    void execute() throws ServiceException{
        String path = cruiseShipsCommand.execute(mockReq, mockResp);

        assertEquals(AllPath.CRUISE_SHIPS_PAGE, path);
        verify(mockCruiseShipService, times(1)).countAll();
        verify(mockReq, times(1)).setAttribute("cruiseShips", List.of(testCruiseShip));
    }
}