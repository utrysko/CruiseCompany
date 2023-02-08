package com.cruise.model.service;

import com.cruise.dto.CruiseDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.CruiseShip;
import com.cruise.model.entities.Route;

import java.util.List;

/**
 * Cruise service interface.
 *
 * @author Vasyl Utrysko.
 * @version 1.0
 */
public interface CruiseService {

     /**
      * Calls DAO to get Cruise instance
      *
      * @param id - Cruise id
      * @return Cruise
      */
     Cruise findById(int id) throws ServiceException;

     /**
      * Calls DAO to add cruise
      *
      * @param cruiseDTO - cruise to be added to database
      */
     void create(CruiseDTO cruiseDTO) throws ServiceException;

     /**
      * Calls DAO to get Cruises instances
      *
      * @return List of Cruises
      */
     List<Cruise> getAllCruise() throws ServiceException;

     /**
      * Calls DAO to get number of cruise records in database
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
     List<CruiseShip> getAllFreeShip(Cruise cruise) throws ServiceException;

     /**
      * Calls DAO to get cruises in required order
      *
      * @param orderBy - number of column in database
      * @param limit - number of records to return
      * @param offset - from where to start returning
      * @return List of cruises
      */
     List<Cruise> getCruisesInOrderAndLimit(int orderBy, int limit, int offset) throws ServiceException;

     /**
      * Calls DAO to update cruise in database
      *
      * @param cruiseDTO - instance of CruiseDTO
      */
     void update(CruiseDTO cruiseDTO) throws ServiceException;

     /**
      * Calls DAO to delete cruise from database
      *
      * @param cruise - instance of Cruise
      */
     void delete(Cruise cruise) throws ServiceException;

     /**
      * Calls DAO to change cruise status in database
      *
      * @param cruise - instance of Cruise
      * @param status - new status
      */
     void changeStatus(Cruise cruise, String status) throws ServiceException;

     /**
      * Calls DAO to add new ship to cruise. if cruise already has ship, method will replace it
      *
      * @param cruise - instance of Cruise
      * @param cruiseShip - instance of CruiseShip
      */
     void addShipToCruise(CruiseShip cruiseShip, Cruise cruise) throws ServiceException;

     /**
      * Calls DAO to add new route to cruise. if cruise already has route, method will replace it
      *
      * @param cruise - instance of Cruise
      * @param route - instance of Route
      */
     void addRouteToCruise(Route route, Cruise cruise) throws ServiceException;

     /**
      * Calls DAO to change free spaces of cruise in database
      *
      * @param cruise - instance of Cruise
      * @param freeSpaces - new number of free spaces
      */
     void changeFreeSpaces(Cruise cruise, int freeSpaces) throws ServiceException;
}
