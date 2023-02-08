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
import static org.mockito.Mockito.*;

class ChangePasswordCommandTest {

    static HttpServletRequest mockReq;
    static HttpServletResponse mockResp;
    static HttpSession mockSession;
    static UserService mockUserService;
    static ChangePasswordCommand changePasswordCommand;

    @BeforeAll
    static public void globalSetUp(){
        mockReq = mock(HttpServletRequest.class);
        mockResp = mock(HttpServletResponse.class);
        mockUserService = mock(UserService.class);
        changePasswordCommand = new ChangePasswordCommand(mockUserService);
    }

    @BeforeEach
    public void setUp() throws ServiceException {
        reset(mockReq);
        reset(mockResp);
        reset(mockUserService);
        doNothing().when(mockUserService).changePassword(any(User.class), anyString(), anyString());
        when(mockUserService.findById(8)).thenReturn(new User());
        when(mockReq.getParameter("oldPassword")).thenReturn("123");
        when(mockReq.getParameter("newPasswordFirst")).thenReturn("12345");
        when(mockReq.getParameter("newPasswordSecond")).thenReturn("12345");
        when(mockReq.getParameter("userId")).thenReturn("8");
        when(mockReq.getSession()).thenReturn(mockSession);
    }

    @Test
    void execute() throws ServiceException{
        String path = changePasswordCommand.execute(mockReq, mockResp);
        //verify and assert
        assertEquals(AllPath.PERSONAL_CABINET_COMMAND, path);
        verify(mockUserService, times(1)).changePassword(any(User.class), anyString(), anyString());
        verify(mockUserService, times(1)).findById(anyInt());
        verify(mockReq, times(1)).getParameter("oldPassword");
        verify(mockReq, times(1)).getParameter("newPasswordFirst");
        verify(mockReq, times(1)).getParameter("newPasswordSecond");
    }
}