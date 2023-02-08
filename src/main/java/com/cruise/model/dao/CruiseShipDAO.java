package com.cruise.model.dao;

import com.cruise.exceptions.DAOException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.CruiseShip;

import java.sql.Date;
import java.util.List;

/**
 * CruiseShip DAO interface.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public interface CruiseShipDAO {

    /**
     * Obtains cruiseShip instance from database
     *
     * @param id - cruiseShip id
     * @return CruiseShip
     */
    CruiseShip findById(int id) throws DAOException;

    /**
     * Obtains cruiseShip instance from database
     *
     * @param name - cruiseShip name
     * @return CruiseShip
     */
    CruiseShip findByName(String name) throws DAOException;

    /**
     * Insert cruiseShip in database
     *
     * @param cruiseShip - instance of CruiseShip
     */
    void create(CruiseShip cruiseShip) throws DAOException;

    /**
     * Obtains all cruiseShips instances from database
     *
     * @return List of cruiseShips
     */
    List<CruiseShip> getAllCruiseShip() throws DAOException;

    /**
     * Obtains number of cruiseShips records in database
     *
     * @return int - number of records
     */
    int countAll() throws DAOException;

    /**
     * Obtains all free by the date of cruise cruiseShips instances from database
     *
     * @param cruise - instance of Cruise
     * @return List of cruiseShips
     */
    List<CruiseShip> getFreeCruiseShip(Cruise cruise) throws DAOException;

    /**
     *Obtains cruiseShips in required order
     *
     * @param orderBy - number of column in database
     * @param limit - number of records to return
     * @param offset - from where to start returning
     * @return List of cruiseShips
     */
    List<CruiseShip> getCruiseShipsInOrderAndLimit(int orderBy, int limit, int offset) throws DAOException;

    /**
     * Update cruiseShip in database
     *
     * @param cruiseShip - instance of CruiseShip
     */
    void update(CruiseShip cruiseShip) throws DAOException;

    /**
     * Delete cruiseShip from database
     *
     * @param cruiseShip - instance of CruiseShip
     */
    void delete(CruiseShip cruiseShip) throws DAOException;

    /**
     * Change cruiseShip status in database
     *
     * @param cruiseShip - instance of CruiseShip
     * @param status - new status
     */
    void changeStatus(CruiseShip cruiseShip, String status) throws DAOException;

    /**
     * Change cruiseShip availableDate in database
     *
     * @param cruiseShip - instance of CruiseShip
     * @param date - new available date
     */
    void changeAvailableDate(CruiseShip cruiseShip, Date date) throws DAOException;
}
