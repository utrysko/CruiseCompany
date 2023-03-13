package com.cruise.controller.command.client;

import com.cruise.controller.AllPath;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.User;
import com.cruise.model.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class ReplenishBalanceCommandTest {

    static HttpServletRequest mockReq;
    static HttpServletResponse mockResp;
    static HttpSession mockSession;
    static UserService mockUserService;
    static ReplenishBalanceCommand replenishBalanceCommand;

    @BeforeAll
    static public void globalSetUp(){
        mockReq = mock(HttpServletRequest.class);
        mockResp = mock(HttpServletResponse.class);
        mockUserService = mock(UserService.class);
        mockSession = mock(HttpSession.class);
        replenishBalanceCommand = new ReplenishBalanceCommand(mockUserService);
    }

    @BeforeEach
    public void setUp() throws ServiceException {
        reset(mockReq);
        reset(mockResp);
        reset(mockUserService);
        when(mockUserService.findById(4)).thenReturn(new User());
        doNothing().when(mockUserService).changeBalance(any(User.class), anyDouble());
        when(mockReq.getParameter("userId")).thenReturn("4");
        when(mockReq.getParameter("sum")).thenReturn("50");
        when(mockReq.getSession()).thenReturn(mockSession);
    }

    @Test
    void execute() throws ServiceException{
        String path = replenishBalanceCommand.execute(mockReq, mockResp);
        //verify and assert
        assertEquals(AllPath.PERSONAL_CABINET_COMMAND, path);
        verify(mockUserService, times(1)).findById(4);
        verify(mockUserService, times(1)).changeBalance(any(User.class), anyDouble());
        verify(mockReq, times(1)).getParameter("userId");
        verify(mockReq, times(1)).getParameter("sum");
    }
}