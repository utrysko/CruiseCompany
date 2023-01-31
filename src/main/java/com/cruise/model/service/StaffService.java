package com.cruise.model.service;

import com.cruise.dto.StaffDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Staff;

import java.util.List;

/**
 * User service interface .
 *
 * @author Vasyl Utrysko.
 */
public interface StaffService {
    Staff findById(int id) throws ServiceException;
    List<Staff> getStaffInOrderAndLimit(int orderBy, int limit, int offset, int cruiseShipId) throws ServiceException;
    List<Staff> getAllByCruiseId(int cruiseId) throws ServiceException;
    int countAll(int cruiseShipId) throws ServiceException;
    void create(StaffDTO staffDTO) throws ServiceException;
    void delete(Staff staff) throws ServiceException;
    void update(StaffDTO staffDTO) throws ServiceException;
}