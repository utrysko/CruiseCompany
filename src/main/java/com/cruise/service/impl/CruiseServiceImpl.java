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

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class CruiseServiceImpl implements CruiseService {

    private CruiseDAO cruiseDAO;
    private CruiseShipDAO cruiseShipDAO;
    public CruiseServiceImpl(CruiseDAO cruiseDAO, CruiseShipDAO cruiseShipDAO){
        this.cruiseShipDAO = cruiseShipDAO;
        this.cruiseDAO = cruiseDAO;
    }
    @Override
    public Cruise findById(int id) throws ServiceException {
        Cruise cruise;
        try {
            cruise = cruiseDAO.findById(id);
        } catch (DAOException e) {
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
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Cruise> getAllCruise() throws ServiceException {
        List<Cruise> cruises;
        try {
            cruises = cruiseDAO.getAllCruise();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return cruises;
    }

    @Override
    public List<CruiseShip> getAllFreeShip(Cruise cruise) throws ServiceException {
        List<CruiseShip> cruiseShips;
        try {
            cruiseShips = cruiseShipDAO.getFreeCruiseShip(cruise);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return cruiseShips;
    }

    @Override
    public List<Cruise> getCruisesInOrderAndLimit(int orderBy, int limit, int offset) throws ServiceException {
        List<Cruise> cruises;
        try {
            cruises = cruiseDAO.getCruisesInOrderAndLimit(orderBy, limit, offset);
        } catch (DAOException e) {
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
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(Cruise cruise) throws ServiceException {
        try {
            cruiseDAO.delete(cruise);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void changeStatus(Cruise cruise, String status) throws ServiceException {
        try {
            cruiseDAO.changeStatus(cruise, status);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void addShipToCruise(CruiseShip cruiseShip, Cruise cruise) throws ServiceException {
        try {
            cruiseDAO.addShipToCruise(cruiseShip, cruise);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void addRouteToCruise(Route route, Cruise cruise) throws ServiceException {
        try {
            cruiseDAO.addRouteToCruise(route, cruise);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void changeFreeSpaces(Cruise cruise, int freeSpaces) throws ServiceException {
        try {
            cruiseShipDAO.changeFreeSpaces(cruise.getCruiseShip(), freeSpaces);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void checkAllCruise() throws ServiceException {
        List<Cruise> cruises = getAllCruise();
        for(Cruise cruise : cruises){
            if (!cruise.getStart().after(Date.valueOf(LocalDate.now())) && !cruise.getStatus().equals("Started")){
                changeStatus(cruise, "Started");
            }
            if (!cruise.getEnd().after(Date.valueOf(LocalDate.now())) && !cruise.getStatus().equals("Started")){
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