package com.cruise.model.service.impl;

import com.cruise.exceptions.CruiseCantFindException;
import com.cruise.model.dao.CruiseDAO;
import com.cruise.model.dao.CruiseShipDAO;
import com.cruise.dto.CruiseDTO;
import com.cruise.exceptions.DAOException;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.CruiseShip;
import com.cruise.model.entities.Route;
import com.cruise.model.service.CruiseService;
import com.cruise.utils.ConvertorUtil;
import com.cruise.utils.ValidationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Class represents implementation of CruiseService interface.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class CruiseServiceImpl implements CruiseService {

    private static final Logger LOG = LogManager.getLogger(CruiseServiceImpl.class);
    private final CruiseDAO cruiseDAO;
    private final CruiseShipDAO cruiseShipDAO;

    public CruiseServiceImpl(CruiseDAO cruiseDAO, CruiseShipDAO cruiseShipDAO) {
        this.cruiseShipDAO = cruiseShipDAO;
        this.cruiseDAO = cruiseDAO;
    }

    @Override
    public Cruise findById(int id) throws ServiceException {
        ValidationUtil.validateDigitField(id);
        Optional<Cruise> cruise;
        try {
            cruise = cruiseDAO.findById(id);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        if (cruise.isEmpty()) throw new CruiseCantFindException();
        return cruise.get();
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
        } catch (DAOException e) {
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
        } catch (DAOException e) {
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
            if (cruise.getCruiseShip() != null) {
                ValidationUtil.validateCruiseFreeSpaces(cruise.getCruiseShip().getCapacity(), cruise.getFreeSpaces());
                cruiseShipDAO.changeAvailableDate(cruise.getCruiseShip(), cruise.getEnd());
            }
            cruiseDAO.update(cruise);
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
        ValidationUtil.validateAddingShip(cruise, cruiseShip);
        try {
            CruiseShip cruiseShipOld = cruise.getCruiseShip();
            cruiseDAO.addShipToCruise(cruiseShip, cruise);
            cruiseShipDAO.changeAvailableDate(cruiseShip, cruise.getEnd());
            if (cruiseShipOld != null) {
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
            cruiseDAO.changeFreeSpaces(cruise, freeSpaces);
        } catch (DAOException e) {
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }


    private void checkCruises(List<Cruise> cruises) throws ServiceException {
        for (Cruise cruise : cruises) {
            if (!cruise.getStart().after(Date.valueOf(LocalDate.now())) && !cruise.getStatus().equals("Started")) {
                changeStatus(cruise, "Started");
                cruise.setStatus("Started");
            }
            if (!cruise.getEnd().after(Date.valueOf(LocalDate.now())) && !cruise.getStatus().equals("Ended")) {
                changeStatus(cruise, "Ended");
                cruise.setStatus("Ended");
            }
        }
    }

    private void validateCruise(CruiseDTO cruiseDTO) throws ServiceException {
        ValidationUtil.validateStartDateCruise(cruiseDTO.getStart());
        ValidationUtil.validateEndDateCruise(cruiseDTO.getEnd(), cruiseDTO.getStart());
        ValidationUtil.validateDigitField(cruiseDTO.getFreeSpaces());
        ValidationUtil.validateCruisePrice(cruiseDTO.getTicketPrice());
    }
}