package com.cruise.service.impl;

import com.cruise.model.dao.CruiseDAO;
import com.cruise.model.dao.TicketDAO;
import com.cruise.model.dao.UserDAO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.Ticket;
import com.cruise.model.entities.User;
import com.cruise.model.service.TicketService;
import com.cruise.model.service.impl.TicketServiceImpl;
import com.mysql.cj.jdbc.Blob;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketServiceImplTest {

    static TicketDAO mockTicketDAO;
    static UserDAO mockUserDAO;
    static CruiseDAO mockCruiseDAO;
    static TicketService ticketService;
    static int ticketId;
    static Ticket testTicket;
    static Blob mockBlob;
    static Cruise testCruise;
    static User testUser;

    @BeforeAll
    static public void globalSetUp() {
        ticketId = 5;
        mockBlob = mock(Blob.class);
        mockTicketDAO = mock(TicketDAO.class);
        mockUserDAO = mock(UserDAO.class);
        mockCruiseDAO = mock(CruiseDAO.class);
        testTicket = new Ticket();
        testTicket.setCruise(new Cruise());
        testTicket.setClientId(7);
        testTicket.setStatus("Payed");
        testTicket.setDocument(mockBlob);
        ticketService = new TicketServiceImpl(mockTicketDAO, mockCruiseDAO, mockUserDAO);
        testCruise = new Cruise();
        testCruise.setFreeSpaces(50);
        testCruise.setTicketPrice(25.0);
        testUser = new User();
        testUser.setBalance(30.0);
    }
    @BeforeEach
    public void setUp() {
        reset(mockCruiseDAO);
        reset(mockTicketDAO);
        reset(mockUserDAO);
        when(mockTicketDAO.findById(ticketId)).thenReturn(Optional.of(testTicket));
        when(mockTicketDAO.findByUserAndCruiseShip(any(User.class), any(Cruise.class))).thenReturn(Optional.of(testTicket));
        when(mockTicketDAO.findAllByUser(any(User.class))).thenReturn(List.of(testTicket));
        when(mockTicketDAO.findByAllByCruise(any(Cruise.class))).thenReturn(List.of(testTicket));
        when(mockTicketDAO.getAllTickets()).thenReturn(List.of(testTicket));
        when(mockTicketDAO.getTicketsInOrderAndLimit(anyInt(), anyInt(), anyInt())).thenReturn(List.of(testTicket));
        when(mockTicketDAO.countAll()).thenReturn(1);
        doNothing().when(mockTicketDAO).create(any(Ticket.class), any(User.class), any(Cruise.class));
        when(mockCruiseDAO.findById(anyInt())).thenReturn(Optional.of(testCruise));
        when(mockUserDAO.findById(anyInt())).thenReturn(Optional.of(testUser));
    }


    @Test
    void findById() throws ServiceException {
        Ticket ticket = ticketService.findById(ticketId);

        verify(mockTicketDAO, times(1)).findById(anyInt());
        assertEquals(testTicket.getClientId(), ticket.getClientId());
        assertThrows(ServiceException.class, () -> ticketService.findById(0));
    }

    @Test
    void findByUserAndCruiseShip() throws ServiceException{
        Ticket ticket = ticketService.findByUserAndCruiseShip(new User(), new Cruise());

        verify(mockTicketDAO, times(1)).findByUserAndCruiseShip(any(User.class), any(Cruise.class));
        assertEquals(testTicket.getClientId(), ticket.getClientId());
    }

    @Test
    void findAllByUser() throws ServiceException{
        List<Ticket> tickets = ticketService.findAllByUser(new User());

        verify(mockTicketDAO, times(1)).findAllByUser(any(User.class));
        assertEquals(testTicket.getClientId(), tickets.get(0).getClientId());
    }

    @Test
    void findByAllByCruise() throws ServiceException{
        List<Ticket> tickets = ticketService.findByAllByCruise(new Cruise());

        verify(mockTicketDAO, times(1)).findByAllByCruise(any(Cruise.class));
        assertEquals(testTicket.getClientId(), tickets.get(0).getClientId());
    }

    @Test
    void getAllTickets() throws ServiceException{
        List<Ticket> tickets = ticketService.getAllTickets();

        verify(mockTicketDAO, times(1)).getAllTickets();
        assertEquals(testTicket.getClientId(), tickets.get(0).getClientId());
    }

    @Test
    void countAll() throws ServiceException{
        int amount = ticketService.countAll();

        verify(mockTicketDAO, times(1)).countAll();
        assertEquals(1, amount);
    }

    @Test
    void getTicketsInOrderAndLimit() throws ServiceException{
        List<Ticket> tickets = ticketService.getTicketsInOrderAndLimit(1, 1, 1);

        verify(mockTicketDAO, times(1)).getTicketsInOrderAndLimit(anyInt(), anyInt(), anyInt());
        assertEquals(testTicket.getClientId(), tickets.get(0).getClientId());
    }

    @Test
    void create() throws ServiceException{
        ticketService.create(testTicket);

        verify(mockTicketDAO, times(1)).create(any(Ticket.class), any(User.class), any(Cruise.class));
        verify(mockCruiseDAO, times(1)).findById(anyInt());
        verify(mockUserDAO, times(1)).findById(anyInt());
    }

    @Test
    void update() throws ServiceException{
        testTicket.setId(23);
        testTicket.setCruiseId(3);
        ticketService.update(testTicket);

        verify(mockTicketDAO, times(1)).update(any(Ticket.class));
    }

    @Test
    void delete() throws ServiceException{
        ticketService.delete(testTicket);

        verify(mockTicketDAO, times(1)).delete(any(Ticket.class));
    }

    @Test
    void changeStatus() throws ServiceException{
        ticketService.changeStatus(testTicket, "Status");

        verify(mockTicketDAO, times(1)).changeStatus(any(Ticket.class), anyString());
    }
}