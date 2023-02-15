package com.cruise.model.dao;

import com.cruise.exceptions.DAOException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.CruiseShip;
import com.cruise.model.entities.Route;

import java.util.List;
import java.util.Optional;

/**
 * Cruise DAO interface.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public interface CruiseDAO {

    /**
     * Obtains cruise instance from database
     *
     * @param id - cruise id
     * @return Cruise
     */
    Optional<Cruise> findById(int id) throws DAOException;

    /**
     * Insert cruise in database
     *
     * @param cruise - instance of Cruise
     */
    void create(Cruise cruise) throws DAOException;

    /**
     * Obtains all cruises instances from database
     *
     * @return List of cruises
     */
    List<Cruise> getAllCruise() throws DAOException;

    /**
     * Obtains number of cruises records in database
     *
     * @return int - number of records
     */
    int countAll() throws DAOException;

    /**
     *Obtains cruises in required order
     *
     * @param orderBy - number of column in database
     * @param limit - number of records to return
     * @param offset - from where to start returning
     * @return List of cruises
     */
    List<Cruise> getCruisesInOrderAndLimit(int orderBy, int limit, int offset) throws DAOException;

    /**
     * Update cruise in database
     *
     * @param cruise - instance of Cruise
     */
    void update(Cruise cruise) throws DAOException;

    /**
     * Delete cruise from database
     *
     * @param cruise - instance of Cruise
     */
    void delete(Cruise cruise) throws DAOException;

    /**
     * Change cruise status in database
     *
     * @param cruise - instance of Cruise
     * @param status - new status
     */
    void changeStatus(Cruise cruise, String status) throws DAOException;

    /**
     * Change free spaces of cruise in database
     *
     * @param cruise - instance of Cruise
     * @param freeSpaces - new number of free spaces
     */
    void changeFreeSpaces(Cruise cruise, int freeSpaces) throws DAOException;

    /**
     * Add new ship to cruise. if cruise already has ship, method will replace it
     *
     * @param cruise - instance of Cruise
     * @param cruiseShip - instance of CruiseShip
     */
    void addShipToCruise(CruiseShip cruiseShip, Cruise cruise) throws DAOException;

    /**
     * Add new route to cruise. if cruise already has route, method will replace it
     *
     * @param cruise - instance of Cruise
     * @param route - instance of Route
     */
    void addRouteToCruise(Route route, Cruise cruise) throws DAOException;

    /**
     * Check if any of cruises is used Route
     *
     * @param idRoute - id of Route instance
     */
    boolean cruiseUsedRoute(int idRoute) throws DAOException;
}
