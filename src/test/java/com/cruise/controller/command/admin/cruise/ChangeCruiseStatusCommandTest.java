package com.cruise.controller.command.admin.cruise;

import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.service.CruiseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChangeCruiseStatusCommandTest {

    static CruiseService mockCruiseService;
    static HttpServletRequest mockReq;
    static HttpServletResponse mockResp;
    static ChangeCruiseStatusCommand changeCruiseStatusCommand;

    @BeforeAll
    static public void globalSetUp(){
        mockCruiseService = mock(CruiseService.class);
        mockReq = mock(HttpServletRequest.class);
        mockResp = mock(HttpServletResponse.class);
        changeCruiseStatusCommand = new ChangeCruiseStatusCommand(mockCruiseService);
    }

    @BeforeEach
    public void setUp() throws ServiceException {
        when(mockReq.getParameter("cruiseId")).thenReturn("7");
        when(mockReq.getParameter("status")).thenReturn("Available");
        when(mockCruiseService.findById(7)).thenReturn(new Cruise());
    }

    @Test
    void execute() throws ServiceException{
        changeCruiseStatusCommand.execute(mockReq, mockResp);

        verify(mockCruiseService, times(1)).findById(7);
        verify(mockReq, times(1)).getParameter("status");
        verify(mockCruiseService, times(1)).changeStatus(any(Cruise.class), anyString());
    }
}