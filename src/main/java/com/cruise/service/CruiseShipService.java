package com.cruise.service;

import com.cruise.dto.CruiseShipDTO;
import com.cruise.exceptions.DAOException;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Cruise;
import com.cruise.model.CruiseShip;

import java.sql.Date;
import java.util.List;

/**
 * User service interface .
 *
 * @author Vasyl Utrysko.
 */
public interface CruiseShipService {
    CruiseShip findById(int id) throws ServiceException;
    CruiseShip findByName(String name) throws ServiceException;
    void create(CruiseShipDTO cruiseShipDTO) throws ServiceException;
    List<CruiseShip> getAllCruiseShip() throws ServiceException;
    List<CruiseShip> getAllFreeCruiseShip(Cruise cruise) throws ServiceException;
    List<CruiseShip> getCruiseShipsInOrderAndLimit(int orderBy, int limit, int offset) throws ServiceException;
    void update(CruiseShipDTO cruiseShipDTO) throws ServiceException;
    void delete(CruiseShip cruiseShip) throws ServiceException;
    void changeFreeSpaces(CruiseShip cruiseShip, int freeSpaces) throws ServiceException;
    void changeStatus(CruiseShip cruiseShip, String status) throws ServiceException;
    void changeAvailableDate(CruiseShip cruiseShip, Date date) throws ServiceException;
    void checkAllShips() throws ServiceException;
}
