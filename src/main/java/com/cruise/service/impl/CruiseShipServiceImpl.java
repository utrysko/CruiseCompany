package com.cruise.service.impl;

import com.cruise.dao.CruiseShipDAO;
import com.cruise.dto.CruiseShipDTO;
import com.cruise.exceptions.DAOException;
import com.cruise.exceptions.ServiceException;
import com.cruise.exceptions.constants.ExceptionMessage;
import com.cruise.model.Cruise;
import com.cruise.model.CruiseShip;
import com.cruise.service.CruiseShipService;
import com.cruise.utils.ConvertorUtil;
import com.cruise.utils.ValidationUtil;
import com.cruise.utils.constants.Regex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class CruiseShipServiceImpl implements CruiseShipService {
    private final CruiseShipDAO cruiseShipDAO;

    public CruiseShipServiceImpl(CruiseShipDAO cruiseShipDAO) {
        this.cruiseShipDAO = cruiseShipDAO;
    }

    @Override
    public CruiseShip findById(int id) throws ServiceException{
        CruiseShip cruiseShip;
        try {
            cruiseShip = cruiseShipDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return cruiseShip;
    }

    @Override
    public CruiseShip findByName(String name) throws ServiceException {
        CruiseShip cruiseShip;
        try {
            cruiseShip = cruiseShipDAO.findByName(name);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return cruiseShip;
    }

    public void create(CruiseShipDTO cruiseShipDTO) throws ServiceException {
        CruiseShip ship = findByName(cruiseShipDTO.getName());
        if (ship != null){
            throw new ServiceException("name already in use");
        }
        validateCruiseShip(cruiseShipDTO);
        CruiseShip cruiseShip = ConvertorUtil.convertDTOtoCruiseShip(cruiseShipDTO);
        try {
            cruiseShipDAO.create(cruiseShip);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<CruiseShip> getAllCruiseShip() throws ServiceException {
        List<CruiseShip> cruiseShips;
        try {
            cruiseShips = cruiseShipDAO.getAllCruiseShip();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return cruiseShips;
    }

    @Override
    public List<CruiseShip> getAllFreeCruiseShip(Cruise cruise) throws ServiceException {
        List<CruiseShip> cruiseShips;
        try {
            cruiseShips = cruiseShipDAO.getFreeCruiseShip(cruise);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return cruiseShips;
    }

    @Override
    public List<CruiseShip> getCruiseShipsInOrderAndLimit(int orderBy, int limit, int offset) throws ServiceException {
        List<CruiseShip> cruiseShips;
        try {
            cruiseShips = cruiseShipDAO.getCruiseShipsInOrderAndLimit(orderBy, limit, offset);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return cruiseShips;
    }


    @Override
    public void update(CruiseShipDTO cruiseShipDTO) throws ServiceException {
        CruiseShip ship = findByName(cruiseShipDTO.getName());
        if (ship != null){
            if (cruiseShipDTO.getId() != ship.getId()){
                throw new ServiceException("name already in use");
            }
        }
        validateCruiseShip(cruiseShipDTO);
        CruiseShip cruiseShip = ConvertorUtil.convertDTOtoCruiseShip(cruiseShipDTO);
        try {
            cruiseShipDAO.update(cruiseShip);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(CruiseShip cruiseShip) throws ServiceException {
        try {
            cruiseShipDAO.delete(cruiseShip);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void changeFreeSpaces(CruiseShip cruiseShip, int freeSpaces) throws ServiceException {
        try {
            cruiseShipDAO.changeFreeSpaces(cruiseShip, freeSpaces);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void changeStatus(CruiseShip cruiseShip, String status) throws ServiceException {
        try {
            cruiseShipDAO.changeStatus(cruiseShip, status);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void changeAvailableDate(CruiseShip cruiseShip, Date date) throws ServiceException {
        try {
            cruiseShipDAO.changeAvailableDate(cruiseShip, date);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void checkAllShips() throws ServiceException {
        List<CruiseShip> ships = getAllCruiseShip();
        for(CruiseShip ship : ships){
            if (ship.getAvailableFrom().after(Date.valueOf(LocalDate.now())) && !ship.getStatus().equals("Used")){
                changeStatus(ship, "Used");
            }
            if (!ship.getAvailableFrom().after(Date.valueOf(LocalDate.now())) && ship.getStatus().equals("Used")){
                changeStatus(ship, "Available");
            }
        }
    }

    private void validateCruiseShip(CruiseShipDTO cruiseShipDTO) throws ServiceException {
        ValidationUtil.validateField(cruiseShipDTO.getName(), Regex.PORT_REGEX, ExceptionMessage.ERROR_SHIP_NAME);
        ValidationUtil.validateAllDigitCruiseFields(cruiseShipDTO.getCapacity());
        ValidationUtil.validateCruiseFreeSpaces(cruiseShipDTO.getCapacity(), cruiseShipDTO.getFreeSpaces());
    }
}