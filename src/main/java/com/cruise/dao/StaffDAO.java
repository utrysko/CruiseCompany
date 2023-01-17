package com.cruise.dao;

import com.cruise.exceptions.DAOException;
import com.cruise.model.CruiseShip;
import com.cruise.model.Staff;

import java.util.List;

public interface StaffDAO {

    Staff findById(int id) throws DAOException;
    List<Staff> getStaffInOrderAndLimit(int orderBy, int limit, int offset, int cruiseShipId) throws DAOException;
    List<Staff> getAllByCruiseId(int cruiseId) throws DAOException;
    void create(Staff staff) throws DAOException;
    void delete(Staff staff) throws DAOException;
    void update(Staff staff) throws DAOException;
}
