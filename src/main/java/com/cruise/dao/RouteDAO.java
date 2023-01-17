package com.cruise.dao;

import com.cruise.exceptions.DAOException;
import com.cruise.model.Route;

import java.util.List;

public interface RouteDAO {

    Route findById(int id) throws DAOException;
    List<Route> getAllRoutes() throws DAOException;
    List<Route> getRoutesInOrderAndLimit(int orderBy, int limit, int offset) throws DAOException;
    void create(Route route) throws DAOException;
    void update(Route route) throws DAOException;
    void delete(Route route) throws DAOException;
}
