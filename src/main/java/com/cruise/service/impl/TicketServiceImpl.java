package com.cruise.service.impl;

import com.cruise.dao.CruiseDAO;
import com.cruise.dao.TicketDAO;
import com.cruise.dao.UserDAO;
import com.cruise.exceptions.DAOException;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Cruise;
import com.cruise.model.Ticket;
import com.cruise.model.User;
import com.cruise.service.TicketService;
import com.cruise.utils.ValidationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TicketServiceImpl implements TicketService {

    private static final Logger LOG = LogManager.getLogger(TicketServiceImpl.class);

    private final TicketDAO ticketDAO;
    private final UserDAO userDAO;
    private final CruiseDAO cruiseDAO;
    public TicketServiceImpl(TicketDAO ticketDAO, CruiseDAO cruiseDAO, UserDAO userDAO){
        this.ticketDAO = ticketDAO;
        this.cruiseDAO = cruiseDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Ticket findById(int id) throws ServiceException {
        Ticket ticket;
        ValidationUtil.validateAllDigitCruiseFields(id);
        try {
            ticket = ticketDAO.findById(id);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
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
            LOG.error(e.getMessage());
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
            LOG.error(e.getMessage());
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
            LOG.error(e.getMessage());
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
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return tickets;
    }

    @Override
    public int countAll() throws ServiceException {
        int amount;
        try {
            amount = ticketDAO.countAll();
        } catch (DAOException e){
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return amount;
    }

    @Override
    public List<Ticket> getTicketsInOrderAndLimit(int orderBy, int limit, int offset) throws ServiceException {
        List<Ticket> tickets;
        try {
            tickets = ticketDAO.getTicketsInOrderAndLimit(orderBy, limit, offset);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return tickets;
    }

    @Override
    public void create(Ticket ticket) throws ServiceException {
        try {
            Cruise cruise = cruiseDAO.findById(ticket.getCruiseId());
            User user = userDAO.findById(ticket.getClientId());
            ticketDAO.create(ticket, user, cruise);
        } catch (DAOException e){
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void update(Ticket ticket) throws ServiceException {
        try {
            ticketDAO.update(ticket);
        } catch (DAOException e){
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(Ticket ticket) throws ServiceException {
        try {
            ticketDAO.delete(ticket);
        } catch (DAOException e){
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void changeStatus(Ticket ticket, String status) throws ServiceException {
        try {
            ticketDAO.changeStatus(ticket, status);
        } catch (DAOException e){
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }
}
