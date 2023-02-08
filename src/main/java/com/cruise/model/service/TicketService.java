package com.cruise.model.service;

import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.Ticket;
import com.cruise.model.entities.User;

import java.util.List;

/**
 * Ticket service interface.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public interface TicketService {

    /**
     * Calls DAO to get ticket instance from database
     *
     * @param id - ticket id
     * @return Ticket
     */
    Ticket findById(int id) throws ServiceException;

    /**
     * Calls DAO to get ticket instance from database
     *
     * @param user - user instance
     * @param cruise - cruise instance
     * @return Ticket
     */
    Ticket findByUserAndCruiseShip(User user, Cruise cruise) throws ServiceException;

    /**
     * Calls DAO to get ticket instance from database
     *
     * @param user - user instance
     * @return Ticket
     */
    List<Ticket> findAllByUser(User user) throws ServiceException;

    /**
     * Calls DAO to get ticket instance from database
     *
     * @param cruise - cruise instance
     * @return Ticket
     */
    List<Ticket> findByAllByCruise(Cruise cruise) throws ServiceException;

    /**
     * Calls DAO to get number of tickets records in database
     *
     * @return number of records(int)
     */
    int countAll() throws ServiceException;

    /**
     * Calls DAO to get all tickets instances from database
     *
     * @return List of tickets
     */
    List<Ticket> getAllTickets() throws ServiceException;

    /**
     * Calls DAO to get tickets in required order
     *
     * @param orderBy - number of column in database
     * @param limit - number of records to return
     * @param offset - from where to start returning
     * @return List of tickets
     */
    List<Ticket> getTicketsInOrderAndLimit(int orderBy,int limit,int offset) throws ServiceException;

    /**
     * Calls DAO to create ticket in database
     *
     * @param ticket - instance of Ticket
     */
    void create(Ticket ticket) throws ServiceException;

    /**
     * Calls DAO to update ticket in database
     *
     * @param ticket - instance of Ticket
     */
    void update(Ticket ticket) throws ServiceException;

    /**
     * Calls DAO to delete ticket from database
     *
     * @param ticket - instance of Ticket
     */
    void delete(Ticket ticket) throws ServiceException;

    /**
     * Calls DAO to change ticket status in database
     *
     * @param ticket - instance of Ticket
     * @param status - new status
     */
    void changeStatus(Ticket ticket, String status) throws ServiceException;
}
