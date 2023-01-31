package com.cruise.model.dao;

import com.cruise.exceptions.DAOException;
import com.cruise.model.entities.Staff;

import java.util.List;

/**
 * Staff DAO interface.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public interface StaffDAO {

    /**
     * Obtains staff instance from database
     *
     * @param id - staff id
     * @return Staff
     */
    Staff findById(int id) throws DAOException;

    /**
     *Obtains staff in required order
     *
     * @param orderBy - number of column in database
     * @param limit - number of records to return
     * @param offset - from where to start returning
     * @return List of staff
     */
    List<Staff> getStaffInOrderAndLimit(int orderBy, int limit, int offset, int cruiseShipId) throws DAOException;

    /**
     * Obtains list of staff from database by cruiseShip
     *
     * @param cruiseId - cruiseShip id
     * @return List of staff
     */
    List<Staff> getAllByCruiseId(int cruiseId) throws DAOException;

    /**
     * Obtains number of staff records in database by cruiseShip
     *
     * @param cruiseShipId - cruiseShip id
     * @return int - number of records
     */
    int countAll(int cruiseShipId) throws DAOException;

    /**
     * Insert staff in database
     *
     * @param staff - instance of Staff
     */
    void create(Staff staff) throws DAOException;

    /**
     * Delete staff from database
     *
     * @param staff - instance of Staff
     */
    void delete(Staff staff) throws DAOException;

    /**
     * Update staff in database
     *
     * @param staff - instance of Staff
     */
    void update(Staff staff) throws DAOException;
}
