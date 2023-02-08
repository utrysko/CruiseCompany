package com.cruise.model.service.impl;

import com.cruise.model.dao.CruiseShipDAO;
import com.cruise.dto.CruiseShipDTO;
import com.cruise.exceptions.DAOException;
import com.cruise.exceptions.ServiceException;
import com.cruise.exceptions.constants.ExceptionMessage;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.CruiseShip;
import com.cruise.model.service.CruiseShipService;
import com.cruise.utils.ConvertorUtil;
import com.cruise.utils.ValidationUtil;
import com.cruise.utils.constants.Regex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Class represents implementation of CruiseShipService interface.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class CruiseShipServiceImpl implements CruiseShipService {

    private static final Logger LOG = LogManager.getLogger(CruiseShipServiceImpl.class);
    private final CruiseShipDAO cruiseShipDAO;

    public CruiseShipServiceImpl(CruiseShipDAO cruiseShipDAO) {
        this.cruiseShipDAO = cruiseShipDAO;
    }

    @Override
    public CruiseShip findById(int id) throws ServiceException{
        CruiseShip cruiseShip;
        ValidationUtil.validateDigitField(id);
        try {
            cruiseShip = cruiseShipDAO.findById(id);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
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
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return cruiseShip;
    }

    public void create(CruiseShipDTO cruiseShipDTO) throws ServiceException {
        CruiseShip ship = findByName(cruiseShipDTO.getName());
        if (ship != null){
            throw new ServiceException("name already used");
        }
        validateCruiseShip(cruiseShipDTO);
        CruiseShip cruiseShip = ConvertorUtil.convertDTOtoCruiseShip(cruiseShipDTO);
        try {
            cruiseShipDAO.create(cruiseShip);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<CruiseShip> getAllCruiseShip() throws ServiceException {
        List<CruiseShip> cruiseShips;
        try {
            cruiseShips = cruiseShipDAO.getAllCruiseShip();
            checkShips(cruiseShips);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return cruiseShips;
    }

    @Override
    public int countAll() throws ServiceException {
        int amount;
        try {
            amount = cruiseShipDAO.countAll();
        } catch (DAOException e){
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return amount;
    }

    @Override
    public List<CruiseShip> getAllFreeCruiseShip(Cruise cruise) throws ServiceException {
        List<CruiseShip> cruiseShips;
        try {
            cruiseShips = cruiseShipDAO.getFreeCruiseShip(cruise);
            checkShips(cruiseShips);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return cruiseShips;
    }

    @Override
    public List<CruiseShip> getCruiseShipsInOrderAndLimit(int orderBy, int limit, int offset) throws ServiceException {
        List<CruiseShip> cruiseShips;
        try {
            cruiseShips = cruiseShipDAO.getCruiseShipsInOrderAndLimit(orderBy, limit, offset);
            checkShips(cruiseShips);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return cruiseShips;
    }


    @Override
    public void update(CruiseShipDTO cruiseShipDTO) throws ServiceException {
        CruiseShip ship = findByName(cruiseShipDTO.getName());
        if (ship != null && cruiseShipDTO.getId() != ship.getId()){
            throw new ServiceException("name already used");
        }
        validateCruiseShip(cruiseShipDTO);
        CruiseShip cruiseShip = ConvertorUtil.convertDTOtoCruiseShip(cruiseShipDTO);
        try {
            cruiseShipDAO.update(cruiseShip);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(CruiseShip cruiseShip) throws ServiceException {
        if (cruiseShip.getStatus().equals("Used")) throw new ServiceException("Ship is used");
        try {
            cruiseShipDAO.delete(cruiseShip);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void changeStatus(CruiseShip cruiseShip, String status) throws ServiceException {
        try {
            cruiseShipDAO.changeStatus(cruiseShip, status);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void changeAvailableDate(CruiseShip cruiseShip, Date date) throws ServiceException {
        try {
            cruiseShipDAO.changeAvailableDate(cruiseShip, date);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }


    private void checkShips(List<CruiseShip> ships) throws ServiceException {
        for(CruiseShip ship : ships){
            if (ship.getAvailableFrom().after(Date.valueOf(LocalDate.now())) && !ship.getStatus().equals("Used")){
                cruiseShipDAO.changeStatus(ship, "Used");
                changeStatus(ship, "Used");
            }
            if (!ship.getAvailableFrom().after(Date.valueOf(LocalDate.now())) && ship.getStatus().equals("Used")){
                cruiseShipDAO.changeStatus(ship, "Available");
                changeStatus(ship, "Available");
            }
        }
    }

    private void validateCruiseShip(CruiseShipDTO cruiseShipDTO) throws ServiceException {
        ValidationUtil.validateStringField(cruiseShipDTO.getName(), Regex.PORT_REGEX, ExceptionMessage.ERROR_SHIP_NAME);
        ValidationUtil.validateDigitField(cruiseShipDTO.getCapacity());
    }
}