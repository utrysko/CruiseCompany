package com.cruise.controller.command.admin.cruise;

import com.cruise.dto.CruiseDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.service.CruiseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Date;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

class AddCruiseCommandTest {

    static CruiseService mockCruiseService;
    static AddCruiseCommand addCruiseCommand;
    static HttpServletRequest mockReq;
    static HttpServletResponse mockResp;

    @BeforeAll
    static public void globalSetUp(){
        mockCruiseService = mock(CruiseService.class);
        mockReq = mock(HttpServletRequest.class);
        mockResp = mock(HttpServletResponse.class);
        addCruiseCommand = new AddCruiseCommand(mockCruiseService);
    }

    @BeforeEach
    public void setUp() throws ServiceException {
        reset(mockCruiseService);
        reset(mockReq);
        reset(mockResp);
        doNothing().when(mockCruiseService).create(any(CruiseDTO.class));
        when(mockReq.getParameter("startDate")).thenReturn(String.valueOf(Date.valueOf(LocalDate.now().plusDays(2))));
        when(mockReq.getParameter("endDate")).thenReturn(String.valueOf(Date.valueOf(LocalDate.now().plusDays(6))));
        when(mockReq.getParameter("status")).thenReturn("Available");
        when(mockReq.getParameter("ticketPrice")).thenReturn("45.0");
        when(mockReq.getParameter("freeSpaces")).thenReturn("100");
    }

    @Test
    void execute() throws ServiceException{
        addCruiseCommand.execute(mockReq, mockResp);
        //verify
        verify(mockCruiseService, times(1)).create(any(CruiseDTO.class));
        verify(mockReq, times(1)).getParameter("startDate");
        verify(mockReq, times(1)).getParameter("endDate");
        verify(mockReq, times(1)).getParameter("status");
        verify(mockReq, times(1)).getParameter("ticketPrice");
        verify(mockReq, times(1)).getParameter("freeSpaces");
    }
}