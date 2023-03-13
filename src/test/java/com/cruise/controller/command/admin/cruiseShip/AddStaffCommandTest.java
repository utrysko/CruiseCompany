package com.cruise.controller.command.admin.cruiseShip;

import com.cruise.controller.AllPath;
import com.cruise.dto.StaffDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.service.StaffService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class AddStaffCommandTest {

    static HttpServletRequest mockReq;
    static HttpServletResponse mockResp;
    static StaffService mockStaffService;
    static AddStaffCommand addStaffCommand;

    @BeforeAll
    static public void globalSetUp(){
        mockStaffService = mock(StaffService.class);
        mockReq = mock(HttpServletRequest.class);
        mockResp = mock(HttpServletResponse.class);
        addStaffCommand = new AddStaffCommand(mockStaffService);
    }

    @BeforeEach
    public void setUp() throws ServiceException {
        reset(mockStaffService);
        reset(mockReq);
        reset(mockResp);
        doNothing().when(mockStaffService).create(any(StaffDTO.class));
        when(mockReq.getParameter("firstName")).thenReturn("John");
        when(mockReq.getParameter("lastName")).thenReturn("Smith");
        when(mockReq.getParameter("position")).thenReturn("Capitan");
        when(mockReq.getParameter("cruiseShipId")).thenReturn("9");
    }

    @Test
    void execute() throws ServiceException {
        String path = addStaffCommand.execute(mockReq, mockResp);
        //verify and assert
        assertEquals(AllPath.EDIT_STAFF_COMMAND + "&cruiseShipId=" + 9, path);
        verify(mockStaffService, times(1)).create(any(StaffDTO.class));
        verify(mockReq, times(1)).getParameter("firstName");
        verify(mockReq, times(1)).getParameter("lastName");
        verify(mockReq, times(1)).getParameter("position");
        verify(mockReq, times(2)).getParameter("cruiseShipId");
    }
}