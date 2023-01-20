package com.cruise.dao;

import com.cruise.exceptions.DAOException;
import com.cruise.model.Cruise;
import com.cruise.model.Ticket;
import com.cruise.model.User;

import java.util.List;

public interface TicketDAO {
    Ticket findById(int id) throws DAOException;
    Ticket findByUserAndCruiseShip(User user, Cruise cruise) throws DAOException;
    List<Ticket> findAllByUser(User user) throws DAOException;
    List<Ticket> findByAllByCruise(Cruise cruise) throws DAOException;
    List<Ticket> getAllTickets() throws DAOException;
    int countAll() throws DAOException;
    List<Ticket> getTicketsInOrderAndLimit(int orderBy, int limit, int offset) throws DAOException;
    void create(Ticket ticket, User user, Cruise cruise) throws DAOException;
    void update(Ticket ticket) throws DAOException;
    void delete(Ticket ticket) throws DAOException;
    void changeStatus(Ticket ticket, String status) throws DAOException;
}
