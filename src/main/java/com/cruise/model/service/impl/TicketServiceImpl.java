package com.cruise.model.service.impl;

import com.cruise.model.dao.CruiseDAO;
import com.cruise.model.dao.TicketDAO;
import com.cruise.model.dao.UserDAO;
import com.cruise.exceptions.DAOException;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.Ticket;
import com.cruise.model.entities.User;
import com.cruise.model.service.TicketService;
import com.cruise.utils.ValidationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Class represents implementation of TicketService interface.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
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
        ValidationUtil.validateDigitField(id);
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
            if (cruise.getFreeSpaces() < 1 || user.getBalance() < cruise.getTicketPrice()){
                throw new ServiceException("Cruise don't have free spaces or your balance is less then ticket price");
            }
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
