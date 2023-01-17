package com.cruise.service.impl;

import com.cruise.dao.RouteDAO;
import com.cruise.dto.RouteDTO;
import com.cruise.exceptions.DAOException;
import com.cruise.exceptions.ServiceException;
import com.cruise.exceptions.constants.ExceptionMessage;
import com.cruise.model.Route;
import com.cruise.service.RouteService;
import com.cruise.utils.ConvertorUtil;
import com.cruise.utils.ValidationUtil;
import com.cruise.utils.constants.Regex;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private final RouteDAO routeDao;

    public RouteServiceImpl(RouteDAO routeDao) {
        this.routeDao = routeDao;
    }
    @Override
    public Route findById(int id) throws ServiceException {
        Route route;
        try {
            route = routeDao.findById(id);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return route;
    }

    @Override
    public List<Route> getAllRoutes() throws ServiceException{
        List<Route> routes;
        try {
            routes = routeDao.getAllRoutes();
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return routes;
    }

    @Override
    public List<Route> getRoutesInOrderAndLimit(int orderBy, int limit, int offset) throws ServiceException {
        List<Route> routes;
        try {
            routes = routeDao.getRoutesInOrderAndLimit(orderBy, limit, offset);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return routes;
    }


    @Override
    public void create(RouteDTO routeDTO) throws ServiceException{
        validateRoute(routeDTO);
        Route route = ConvertorUtil.convertDTOtoRoute(routeDTO);
        try {
            routeDao.create(route);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void update(RouteDTO routeDTO) throws ServiceException{
        validateRoute(routeDTO);
        Route route = ConvertorUtil.convertDTOtoRoute(routeDTO);
        try {
            routeDao.update(route);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(int routeId) throws ServiceException{
        try {
            Route route = routeDao.findById(routeId);
            routeDao.delete(route);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }
    private void validateRoute(RouteDTO routeDTO) throws ServiceException{
        ValidationUtil.validateField(routeDTO.getStartPort(), Regex.PORT_REGEX, ExceptionMessage.ERROR_PORT_NAME);
        ValidationUtil.validateField(routeDTO.getEndPort(), Regex.PORT_REGEX, ExceptionMessage.ERROR_PORT_NAME);
        for (String middlePort : routeDTO.getMiddlePorts()){
            ValidationUtil.validateField(middlePort, Regex.PORT_REGEX, ExceptionMessage.ERROR_PORT_NAME);
        }
    }
}
