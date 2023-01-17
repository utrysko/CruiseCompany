package com.cruise.dao;

import com.cruise.exceptions.DAOException;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Cruise;
import com.cruise.model.CruiseShip;

import java.sql.Date;
import java.util.List;

public interface CruiseShipDAO {

    CruiseShip findById(int id) throws DAOException;
    CruiseShip findByName(String name) throws DAOException;
    void create(CruiseShip cruiseShip) throws DAOException;
    List<CruiseShip> getAllCruiseShip() throws DAOException;
    List<CruiseShip> getFreeCruiseShip(Cruise cruise) throws DAOException;
    List<CruiseShip> getCruiseShipsInOrderAndLimit(int orderBy, int limit, int offset) throws DAOException;
    void update(CruiseShip cruiseShip) throws DAOException;
    void delete(CruiseShip cruiseShip) throws DAOException;
    void changeFreeSpaces(CruiseShip cruiseShip, int freeSpaces) throws DAOException;
    void changeStatus(CruiseShip cruiseShip, String status) throws DAOException;
    void changeAvailableDate(CruiseShip cruiseShip, Date date) throws DAOException;
}
