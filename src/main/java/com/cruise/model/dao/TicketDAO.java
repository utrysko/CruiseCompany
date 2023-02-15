package com.cruise.model.dao;

import com.cruise.exceptions.DAOException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.Ticket;
import com.cruise.model.entities.User;

import java.util.List;
import java.util.Optional;

/**
 * Ticket DAO interface.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public interface TicketDAO {

    /**
     * Obtains ticket instance from database
     *
     * @param id - ticket id
     * @return Ticket
     */
    Optional<Ticket> findById(int id) throws DAOException;

    /**
     * Obtains ticket instance from database
     *
     * @param user - user instance
     * @param cruise - cruise instance
     * @return Ticket
     */
    Optional<Ticket> findByUserAndCruiseShip(User user, Cruise cruise) throws DAOException;

    /**
     * Obtains ticket instance from database
     *
     * @param user - user instance
     * @return Ticket
     */
    List<Ticket> findAllByUser(User user) throws DAOException;

    /**
     * Obtains ticket instance from database
     *
     * @param cruise - cruise instance
     * @return Ticket
     */
    List<Ticket> findByAllByCruise(Cruise cruise) throws DAOException;

    /**
     * Obtains all tickets instances from database
     *
     * @return List of tickets
     */
    List<Ticket> getAllTickets() throws DAOException;

    /**
     * Obtains number of tickets records in database
     *
     * @return int - number of records
     */
    int countAll() throws DAOException;

    /**
     *Obtains tickets in required order
     *
     * @param orderBy - number of column in database
     * @param limit - number of records to return
     * @param offset - from where to start returning
     * @return List of tickets
     */
    List<Ticket> getTicketsInOrderAndLimit(int orderBy, int limit, int offset) throws DAOException;

    /**
     * Insert ticket in database
     *
     * @param cruise - instance of Cruise
     * @param user - instance of User
     * @param ticket - instance of Ticket
     */
    void create(Ticket ticket, User user, Cruise cruise) throws DAOException;

    /**
     * Update ticket in database
     *
     * @param ticket - instance of Ticket
     */
    void update(Ticket ticket) throws DAOException;

    /**
     * Delete ticket from database
     *
     * @param ticket - instance of Ticket
     */
    void delete(Ticket ticket) throws DAOException;

    /**
     * Change ticket status in database
     *
     * @param ticket - instance of Ticket
     * @param status - new status
     */
    void changeStatus(Ticket ticket, String status) throws DAOException;
}
