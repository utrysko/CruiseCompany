package com.cruise.model.dao;

import com.cruise.exceptions.DAOException;
import com.cruise.model.entities.Route;

import java.util.List;
import java.util.Optional;

/**
 * Route DAO interface.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public interface RouteDAO {

    /**
     * Obtains route instance from database
     *
     * @param id - route id
     * @return Route
     */
    Optional<Route> findById(int id) throws DAOException;

    /**
     * Obtains all routes instances from database
     *
     * @return List of routes
     */
    List<Route> getAllRoutes() throws DAOException;

    /**
     * Obtains number of routes records in database
     *
     * @return int - number of records
     */
    int countAll() throws DAOException;

    /**
     *Obtains routes in required order
     *
     * @param orderBy - number of column in database
     * @param limit - number of records to return
     * @param offset - from where to start returning
     * @return List of routes
     */
    List<Route> getRoutesInOrderAndLimit(int orderBy, int limit, int offset) throws DAOException;

    /**
     * Insert route in database
     *
     * @param route - instance of Route
     */
    void create(Route route) throws DAOException;

    /**
     * Update route in database
     *
     * @param route - instance of Route
     */
    void update(Route route) throws DAOException;

    /**
     * Delete route from database
     *
     * @param route - instance of Route
     */
    void delete(Route route) throws DAOException;
}
