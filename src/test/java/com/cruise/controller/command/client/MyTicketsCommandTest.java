package com.cruise.controller.command.client;

import com.cruise.controller.AllPath;
import com.cruise.dto.UserDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Ticket;
import com.cruise.model.entities.User;
import com.cruise.model.service.TicketService;
import com.cruise.model.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MyTicketsCommandTest {

    static HttpServletRequest mockReq;
    static HttpServletResponse mockResp;
    static TicketService mockTicketService;
    static UserService mockUserService;
    static HttpSession mockSession;
    static MyTicketsCommand myTicketsCommand;
    static UserDTO testUserDTO;
    static User testUser;

    @BeforeAll
    static public void globalSetUp() {
        mockReq = mock(HttpServletRequest.class);
        mockResp = mock(HttpServletResponse.class);
        mockUserService = mock(UserService.class);
        mockTicketService = mock(TicketService.class);
        mockSession = mock(HttpSession.class);
        myTicketsCommand = new MyTicketsCommand(mockTicketService, mockUserService);
        testUser = new User();
        testUser.setId(3);
        testUserDTO = new UserDTO();
        testUserDTO.setId(3);
    }

    @BeforeEach
    public void setUp() throws ServiceException {
        reset(mockReq);
        reset(mockResp);
        reset(mockUserService);
        reset(mockTicketService);
        when(mockReq.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(testUserDTO);
        when(mockUserService.findById(3)).thenReturn(testUser);
        when(mockTicketService.findAllByUser(testUser)).thenReturn(List.of(new Ticket()));
        doNothing().when(mockReq).setAttribute(anyString(), any());
    }

    @Test
    void execute() throws ServiceException {
        String path = myTicketsCommand.execute(mockReq, mockResp);
        //verify and assert
        assertEquals(AllPath.MY_TICKETS_PAGE, path);
        verify(mockReq, times(1)).getSession();
        verify(mockSession, times(1)).getAttribute("user");
        verify(mockUserService, times(1)).findById(testUserDTO.getId());
        verify(mockTicketService, times(1)).findAllByUser(testUser);
        verify(mockReq, times(1)).setAttribute(anyString(), any());
    }
}