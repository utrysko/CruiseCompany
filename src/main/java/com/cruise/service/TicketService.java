package com.cruise.service;

import com.cruise.exceptions.ServiceException;
import com.cruise.model.Cruise;
import com.cruise.model.Ticket;
import com.cruise.model.User;

import java.util.List;

/**
 * User service interface .
 *
 * @author Vasyl Utrysko.
 */
public interface TicketService {
    Ticket findById(int id) throws ServiceException;
    Ticket findByUserAndCruiseShip(User user, Cruise cruise) throws ServiceException;
    List<Ticket> findAllByUser(User user) throws ServiceException;
    List<Ticket> findByAllByCruise(Cruise cruise) throws ServiceException;
    int countAll() throws ServiceException;
    List<Ticket> getAllTickets() throws ServiceException;
    List<Ticket> getTicketsInOrderAndLimit(int orderBy,int limit,int offset) throws ServiceException;
    void create(Ticket ticket) throws ServiceException;
    void update(Ticket ticket) throws ServiceException;
    void delete(Ticket ticket) throws ServiceException;
    void changeStatus(Ticket ticket, String status) throws ServiceException;
}
