package com.cruise.model.service.impl;

import com.cruise.exceptions.RouteCantFindException;
import com.cruise.model.dao.CruiseDAO;
import com.cruise.model.dao.RouteDAO;
import com.cruise.dto.RouteDTO;
import com.cruise.exceptions.DAOException;
import com.cruise.exceptions.ServiceException;
import com.cruise.exceptions.constants.ExceptionMessage;
import com.cruise.model.entities.Route;
import com.cruise.model.service.RouteService;
import com.cruise.utils.ConvertorUtil;
import com.cruise.utils.ValidationUtil;
import com.cruise.utils.constants.Regex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * Class represents implementation of RouteService interface.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class RouteServiceImpl implements RouteService {
    private static final Logger LOG = LogManager.getLogger(RouteServiceImpl.class);

    private final RouteDAO routeDao;
    private final CruiseDAO cruiseDAO;

    public RouteServiceImpl(RouteDAO routeDao, CruiseDAO cruiseDAO) {
        this.routeDao = routeDao;
        this.cruiseDAO = cruiseDAO;
    }
    @Override
    public Route findById(int id) throws ServiceException {
        Optional<Route> route;
        ValidationUtil.validateDigitField(id);
        try {
            route = routeDao.findById(id);
        } catch (DAOException e){
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        if (route.isEmpty()) throw new RouteCantFindException();
        return route.get();
    }

    @Override
    public List<Route> getAllRoutes() throws ServiceException{
        List<Route> routes;
        try {
            routes = routeDao.getAllRoutes();
        } catch (DAOException e){
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return routes;
    }

    @Override
    public int countAll() throws ServiceException {
        int amount;
        try {
            amount = routeDao.countAll();
        } catch (DAOException e){
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return amount;
    }

    @Override
    public List<Route> getRoutesInOrderAndLimit(int orderBy, int limit, int offset) throws ServiceException {
        List<Route> routes;
        try {
            routes = routeDao.getRoutesInOrderAndLimit(orderBy, limit, offset);
        } catch (DAOException e){
            LOG.error(e.getMessage());
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
            LOG.error(e.getMessage());
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
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(int routeId) throws ServiceException{
        if (cruiseDAO.cruiseUsedRoute(routeId)) throw new ServiceException("route is used");
        try {
            Route route = findById(routeId);
            routeDao.delete(route);
        } catch (DAOException e){
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }
    private void validateRoute(RouteDTO routeDTO) throws ServiceException{
        ValidationUtil.validateStringField(routeDTO.getStartPort(), Regex.PORT_REGEX, ExceptionMessage.ERROR_PORT_NAME);
        ValidationUtil.validateStringField(routeDTO.getEndPort(), Regex.PORT_REGEX, ExceptionMessage.ERROR_PORT_NAME);
        for (String middlePort : routeDTO.getMiddlePorts()){
            ValidationUtil.validateStringField(middlePort, Regex.PORT_REGEX, ExceptionMessage.ERROR_PORT_NAME);
        }
    }
}
