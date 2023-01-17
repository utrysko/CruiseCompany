package com.cruise.service;

import com.cruise.dto.CruiseDTO;
import com.cruise.dto.CruiseShipDTO;
import com.cruise.exceptions.DAOException;
import com.cruise.exceptions.InvalidFormatException;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Cruise;
import com.cruise.model.CruiseShip;
import com.cruise.model.Route;

import java.sql.Date;
import java.util.List;

/**
 * Cruise service interface .
 *
 * @author Vasyl Utrysko.
 */
public interface CruiseService {
     Cruise findById(int id) throws ServiceException;
     void create(CruiseDTO cruiseDTO) throws ServiceException;
     List<Cruise> getAllCruise() throws ServiceException;
     List<CruiseShip> getAllFreeShip(Cruise cruise) throws ServiceException;
     List<Cruise> getCruisesInOrderAndLimit(int orderBy, int limit, int offset) throws ServiceException;
     void update(CruiseDTO cruiseDTO) throws ServiceException;
     void delete(Cruise cruise) throws ServiceException;
     void changeStatus(Cruise cruise, String status) throws ServiceException;
     void addShipToCruise(CruiseShip cruiseShip, Cruise cruise) throws ServiceException;
     void addRouteToCruise(Route route, Cruise cruise) throws ServiceException;
     void changeFreeSpaces(Cruise cruise, int freeSpaces) throws ServiceException;
     void checkAllCruise() throws ServiceException;
}
