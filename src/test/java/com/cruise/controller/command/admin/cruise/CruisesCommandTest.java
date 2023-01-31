package com.cruise.controller.command.admin.cruise;

import com.cruise.controller.AllPath;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.CruiseShip;
import com.cruise.model.entities.Route;
import com.cruise.model.service.CruiseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CruisesCommandTest {

    static CruiseService mockCruiseService;
    static CruisesCommand cruisesCommand;
    static HttpServletRequest mockReq;
    static HttpServletResponse mockResp;
    static Cruise testCruise;

    @BeforeAll
    static public void globalSetUp(){
        mockCruiseService = mock(CruiseService.class);
        mockReq = mock(HttpServletRequest.class);
        mockResp = mock(HttpServletResponse.class);
        cruisesCommand = new CruisesCommand(mockCruiseService);
        testCruise = new Cruise();
        testCruise.setId(4);
        testCruise.setStart(Date.valueOf(LocalDate.now().plusDays(2)));
        testCruise.setEnd(Date.valueOf(LocalDate.now().plusDays(7)));
        testCruise.setStatus("Available");
        testCruise.setCruiseShip(new CruiseShip());
        testCruise.setRoute(new Route());
        testCruise.setFreeSpaces(50);
        testCruise.setTicketPrice(35.0);
    }

    @BeforeEach
    public void setUp() throws ServiceException {
        reset(mockCruiseService);
        reset(mockReq);
        reset(mockResp);
        when(mockCruiseService.getCruisesInOrderAndLimit(anyInt(), anyInt(), anyInt())).thenReturn(List.of(testCruise));
        when(mockCruiseService.countAll()).thenReturn(1);
        doNothing().when(mockReq).setAttribute(anyString(), any());
    }

    @Test
    void execute() throws ServiceException{
        String path = cruisesCommand.execute(mockReq, mockResp);

        assertEquals(AllPath.CRUISES_PAGE, path);
        verify(mockCruiseService, times(1)).countAll();
        verify(mockReq, times(1)).setAttribute("cruises", List.of(testCruise));
    }
}