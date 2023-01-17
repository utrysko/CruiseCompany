package com.cruise.service;

import com.cruise.dto.RouteDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Route;

import java.util.List;

/**
 * Route service interface .
 *
 * @author Vasyl Utrysko.
 */
public interface RouteService {
     Route findById(int id) throws ServiceException;
     List<Route> getAllRoutes() throws ServiceException;
     List<Route> getRoutesInOrderAndLimit(int orderBy, int limit, int offset) throws ServiceException;
     void create(RouteDTO routeDTO) throws ServiceException;
     void update(RouteDTO routeDTO) throws ServiceException;
     void delete(int routeId) throws ServiceException;
}
