package com.cruise.model.service;

import com.cruise.dto.StaffDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Staff;

import java.util.List;

/**
 * Staff service interface.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public interface StaffService {

    /**
     * Calls DAO to get staff instance from database
     *
     * @param id - staff id
     * @param cruiseShipId - ship id
     * @return Staff
     */
    Staff findById(int id, int cruiseShipId) throws ServiceException;

    /**
     * Calls DAO to get staff in required order
     *
     * @param orderBy - number of column in database
     * @param limit - number of records to return
     * @param offset - from where to start returning
     * @return List of staff
     */
    List<Staff> getStaffInOrderAndLimit(int orderBy, int limit, int offset, int cruiseShipId) throws ServiceException;

    /**
     * Calls DAO to get list of staff from database by cruiseShip
     *
     * @param cruiseId - cruiseShip id
     * @return List of staff
     */
    List<Staff> getAllByCruiseId(int cruiseId) throws ServiceException;

    /**
     * Calls DAO to get number of staff records in database by cruiseShip
     *
     * @param cruiseShipId - cruiseShip id
     * @return int - number of records
     */
    int countAll(int cruiseShipId) throws ServiceException;

    /**
     * Calls DAO to insert staff in database
     *
     * @param staffDTO - instance of StaffDTO
     */
    void create(StaffDTO staffDTO) throws ServiceException;

    /**
     * Calls DAO to delete staff from database
     *
     * @param staff - instance of Staff
     */
    void delete(Staff staff) throws ServiceException;

    /**
     * Calls DAO to update staff from database
     *
     * @param staffDTO - instance of StaffDTO
     */
    void update(StaffDTO staffDTO) throws ServiceException;
}
