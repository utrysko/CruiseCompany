package com.cruise.service.impl;

import com.cruise.dao.CruiseDAO;
import com.cruise.dao.CruiseShipDAO;
import com.cruise.dao.TicketDAO;
import com.cruise.dao.UserDAO;
import com.cruise.exceptions.DAOException;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Cruise;
import com.cruise.model.Ticket;
import com.cruise.model.User;
import com.cruise.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TicketServiceImpl implements TicketService {

    private TicketDAO ticketDAO;
    private UserDAO userDAO;
    private CruiseDAO cruiseDAO;
    private CruiseShipDAO cruiseShipDAO;
    public TicketServiceImpl(TicketDAO ticketDAO, CruiseShipDAO cruiseShipDAO, CruiseDAO cruiseDAO, UserDAO userDAO){
        this.ticketDAO = ticketDAO;
        this.cruiseShipDAO = cruiseShipDAO;
        this.cruiseDAO = cruiseDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Ticket findById(int id) throws ServiceException {
        Ticket ticket;
        try {
            ticket = ticketDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return ticket;
    }

    @Override
    public Ticket findByUserAndCruiseShip(User user, Cruise cruise) throws ServiceException {
        Ticket ticket;
        try {
            ticket = ticketDAO.findByUserAndCruiseShip(user, cruise);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return ticket;
    }

    @Override
    public List<Ticket> findAllByUser(User user) throws ServiceException {
        List<Ticket> tickets;
        try {
            tickets = ticketDAO.findAllByUser(user);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return tickets;
    }

    @Override
    public List<Ticket> findByAllByCruise(Cruise cruise) throws ServiceException {
        List<Ticket> tickets;
        try {
            tickets = ticketDAO.findByAllByCruise(cruise);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return tickets;
    }

    @Override
    public List<Ticket> getAllTickets() throws ServiceException {
        List<Ticket> tickets;
        try {
            tickets = ticketDAO.getAllTickets();
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return tickets;
    }

    @Override
    public List<Ticket> getTicketsInOrderAndLimit(int orderBy, int limit, int offset) throws ServiceException {
        List<Ticket> tickets;
        try {
            tickets = ticketDAO.getTicketsInOrderAndLimit(orderBy, limit, offset);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return tickets;
    }

    @Override
    public void create(Ticket ticket) throws ServiceException {
        try {
            Cruise cruise = cruiseDAO.findById(ticket.getCruiseId());
            User user = userDAO.findById(ticket.getClientId());
            int freeSpaces = cruise.getCruiseShip().getFreeSpaces();
            if (freeSpaces == 0){
                throw new ServiceException("Don't have free space");
            }
            if (user.getBalance() < cruise.getTicketPrice()){
                throw new ServiceException("Balance less then ticket price");
            }
            cruiseShipDAO.changeFreeSpaces(cruise.getCruiseShip(), cruise.getCruiseShip().getFreeSpaces() - 1);
            userDAO.changeBalance(user, user.getBalance() - cruise.getTicketPrice());
            ticketDAO.create(ticket);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void update(Ticket ticket) throws ServiceException {
        try {
            ticketDAO.update(ticket);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(Ticket ticket) throws ServiceException {
        try {
            ticketDAO.delete(ticket);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void changeStatus(Ticket ticket, String status) throws ServiceException {
        try {
            ticketDAO.changeStatus(ticket, status);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }
}
