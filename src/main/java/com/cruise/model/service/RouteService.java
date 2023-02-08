package com.cruise.model.service;

import com.cruise.dto.RouteDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Route;

import java.util.List;

/**
 * Route service interface.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public interface RouteService {

     /**
      * Calls DAO to get route instance from database
      *
      * @param id - route id
      * @return Route
      */
     Route findById(int id) throws ServiceException;

     /**
      * Calls DAO to get all routes instances from database
      *
      * @return List of routes
      */
     List<Route> getAllRoutes() throws ServiceException;

     /**
      * Calls DAO to get number of routes records in database
      *
      * @return int - number of records
      */
     int countAll() throws ServiceException;

     /**
      * Calls DAO to get routes in required order
      *
      * @param orderBy - number of column in database
      * @param limit - number of records to return
      * @param offset - from where to start returning
      * @return List of routes
      */
     List<Route> getRoutesInOrderAndLimit(int orderBy, int limit, int offset) throws ServiceException;

     /**
      * Calls DAO to insert route in database
      *
      * @param routeDTO - instance of RouteDTO
      */
     void create(RouteDTO routeDTO) throws ServiceException;

     /**
      * Calls DAO to update route in database
      *
      * @param routeDTO - instance of RouteDTO
      */
     void update(RouteDTO routeDTO) throws ServiceException;

     /**
      * Calls DAO to delete route from database
      *
      * @param routeId - Route id
      */
     void delete(int routeId) throws ServiceException;
}
