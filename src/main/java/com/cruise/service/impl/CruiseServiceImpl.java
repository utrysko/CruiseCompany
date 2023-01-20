package com.cruise.service.impl;

import com.cruise.dao.CruiseDAO;
import com.cruise.dao.CruiseShipDAO;
import com.cruise.dto.CruiseDTO;
import com.cruise.exceptions.DAOException;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Cruise;
import com.cruise.model.CruiseShip;
import com.cruise.model.Route;
import com.cruise.service.CruiseService;
import com.cruise.utils.ConvertorUtil;
import com.cruise.utils.ValidationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class CruiseServiceImpl implements CruiseService {

    private static final Logger LOG = LogManager.getLogger(CruiseServiceImpl.class);

    private final CruiseDAO cruiseDAO;
    private final CruiseShipDAO cruiseShipDAO;
    public CruiseServiceImpl(CruiseDAO cruiseDAO, CruiseShipDAO cruiseShipDAO){
        this.cruiseShipDAO = cruiseShipDAO;
        this.cruiseDAO = cruiseDAO;
    }
    @Override
    public Cruise findById(int id) throws ServiceException {
        Cruise cruise;
        ValidationUtil.validateAllDigitCruiseFields(id);
        try {
            cruise = cruiseDAO.findById(id);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return cruise;
    }


    @Override
    public void create(CruiseDTO cruiseDTO) throws ServiceException {
        validateCruise(cruiseDTO);
        Cruise cruise = ConvertorUtil.convertDTOtoCruise(cruiseDTO);
        try {
            cruiseDAO.create(cruise);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Cruise> getAllCruise() throws ServiceException {
        List<Cruise> cruises;
        try {
            cruises = cruiseDAO.getAllCruise();
            checkCruises(cruises);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return cruises;
    }

    @Override
    public int countAll() throws ServiceException {
        int amount;
        try {
            amount = cruiseDAO.countAll();
        } catch (DAOException e){
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return amount;
    }

    @Override
    public List<CruiseShip> getAllFreeShip(Cruise cruise) throws ServiceException {
        List<CruiseShip> cruiseShips;
        try {
            cruiseShips = cruiseShipDAO.getFreeCruiseShip(cruise);
        } catch (DAOException e){
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return cruiseShips;
    }

    @Override
    public List<Cruise> getCruisesInOrderAndLimit(int orderBy, int limit, int offset) throws ServiceException {
        List<Cruise> cruises;
        try {
            cruises = cruiseDAO.getCruisesInOrderAndLimit(orderBy, limit, offset);
            checkCruises(cruises);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return cruises;
    }


    @Override
    public void update(CruiseDTO cruiseDTO) throws ServiceException {
        validateCruise(cruiseDTO);
        Cruise cruise = ConvertorUtil.convertDTOtoCruise(cruiseDTO);
        try {
            cruiseDAO.update(cruise);
            cruiseShipDAO.changeAvailableDate(cruise.getCruiseShip(), cruise.getEnd());
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(Cruise cruise) throws ServiceException {
        try {
            cruiseDAO.delete(cruise);
            cruiseShipDAO.changeAvailableDate(cruise.getCruiseShip(), Date.valueOf(LocalDate.now()));
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void changeStatus(Cruise cruise, String status) throws ServiceException {
        try {
            cruiseDAO.changeStatus(cruise, status);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void addShipToCruise(CruiseShip cruiseShip, Cruise cruise) throws ServiceException {
        try {
            CruiseShip cruiseShipOld = cruise.getCruiseShip();
            cruiseDAO.addShipToCruise(cruiseShip, cruise);
            cruiseShipDAO.changeAvailableDate(cruiseShip, cruise.getEnd());
            if (cruiseShipOld != null){
                cruiseShipDAO.changeAvailableDate(cruiseShipOld, Date.valueOf(LocalDate.now()));
            }
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void addRouteToCruise(Route route, Cruise cruise) throws ServiceException {
        try {
            cruiseDAO.addRouteToCruise(route, cruise);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void changeFreeSpaces(Cruise cruise, int freeSpaces) throws ServiceException {
        try {
            cruiseShipDAO.changeFreeSpaces(cruise.getCruiseShip(), freeSpaces);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }


    private void checkCruises(List<Cruise> cruises) throws ServiceException {
        for(Cruise cruise : cruises){
            if (!cruise.getStart().after(Date.valueOf(LocalDate.now())) && !cruise.getStatus().equals("Started")){
                cruiseDAO.changeStatus(cruise, "Started");
                changeStatus(cruise, "Started");
            }
            if (!cruise.getEnd().after(Date.valueOf(LocalDate.now())) && !cruise.getStatus().equals("Ended")){
                cruiseDAO.changeStatus(cruise, "Ended");
                changeStatus(cruise, "Ended");
            }
        }
    }

    private void validateCruise(CruiseDTO cruiseDTO) throws ServiceException{
        ValidationUtil.validateStartDateCruise(cruiseDTO.getStart());
        ValidationUtil.validateEndDateCruise(cruiseDTO.getEnd(), cruiseDTO.getStart());
        ValidationUtil.validateCruisePrice(cruiseDTO.getTicketPrice());
    }
}