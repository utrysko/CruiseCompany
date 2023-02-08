package com.cruise.model.service;

import com.cruise.dto.CruiseShipDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.CruiseShip;

import java.sql.Date;
import java.util.List;

/**
 * User service interface .
 *
 * @author Vasyl Utrysko.
 * @version 1.0
 */
public interface CruiseShipService {

    /**
     * Calls DAO to get cruiseShip instance from database
     *
     * @param id - cruiseShip id
     * @return CruiseShip
     */
    CruiseShip findById(int id) throws ServiceException;

    /**
     * Calls DAO to get cruiseShip instance from database
     *
     * @param name - cruiseShip name
     * @return CruiseShip
     */
    CruiseShip findByName(String name) throws ServiceException;

    /**
     * Calls DAO to insert cruiseShip in database
     *
     * @param cruiseShipDTO - instance of CruiseShipDTO
     */
    void create(CruiseShipDTO cruiseShipDTO) throws ServiceException;

    /**
     * Calls DAO to get all cruiseShips instances from database
     *
     * @return List of cruiseShips
     */
    List<CruiseShip> getAllCruiseShip() throws ServiceException;

    /**
     * Calls DAO to get number of cruiseShips records in database
     *
     * @return number of records(int)
     */
    int countAll() throws ServiceException;

    /**
     * Calls DAO to get all free cruiseShips by the date of cruise from database
     *
     * @param cruise - instance of Cruise
     * @return List of cruiseShips
     */
    List<CruiseShip> getAllFreeCruiseShip(Cruise cruise) throws ServiceException;

    /**
     * Calls DAO to get cruiseShips in required order
     *
     * @param orderBy - number of column in database
     * @param limit - number of records to return
     * @param offset - from where to start returning
     * @return List of cruiseShips
     */
    List<CruiseShip> getCruiseShipsInOrderAndLimit(int orderBy, int limit, int offset) throws ServiceException;

    /**
     * Calls DAO to update cruiseShip in database
     *
     * @param cruiseShipDTO - instance of CruiseShipDTO
     */
    void update(CruiseShipDTO cruiseShipDTO) throws ServiceException;

    /**
     * Calls DAO to delete cruiseShip from database
     *
     * @param cruiseShip - instance of CruiseShip
     */
    void delete(CruiseShip cruiseShip) throws ServiceException;

    /**
     * Calls DAO to change cruiseShip status in database
     *
     * @param cruiseShip - instance of CruiseShip
     * @param status - new status
     */
    void changeStatus(CruiseShip cruiseShip, String status) throws ServiceException;

    /**
     * Calls DAO to change cruiseShip availableDate in database
     *
     * @param cruiseShip - instance of CruiseShip
     * @param date - new available date
     */
    void changeAvailableDate(CruiseShip cruiseShip, Date date) throws ServiceException;
}
