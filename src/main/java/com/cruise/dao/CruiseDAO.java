package com.cruise.dao;

import com.cruise.exceptions.DAOException;
import com.cruise.model.Cruise;
import com.cruise.model.CruiseShip;
import com.cruise.model.Route;

import java.util.List;

public interface CruiseDAO {

    Cruise findById(int id) throws DAOException;
    void create(Cruise cruise) throws DAOException;
    List<Cruise> getAllCruise() throws DAOException;
    int countAll() throws DAOException;
    List<Cruise> getCruisesInOrderAndLimit(int orderBy, int limit, int offset) throws DAOException;
    void update(Cruise cruise) throws DAOException;
    void delete(Cruise cruise) throws DAOException;
    void changeStatus(Cruise cruise, String status) throws DAOException;
    void changeFreeSpaces(Cruise cruise, int freeSpaces) throws DAOException;
    void addShipToCruise(CruiseShip cruiseShip, Cruise cruise) throws DAOException;
    void addRouteToCruise(Route route, Cruise cruise) throws DAOException;
    boolean cruiseUsedRoute(int idRoute) throws DAOException;
}
