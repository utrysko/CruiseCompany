package com.cruise.service.impl;

import com.cruise.dao.StaffDAO;
import com.cruise.dto.StaffDTO;
import com.cruise.exceptions.DAOException;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.CruiseShip;
import com.cruise.model.Staff;
import com.cruise.service.StaffService;
import com.cruise.utils.ConvertorUtil;

import java.util.List;

public class StaffServiceImpl implements StaffService {

    private final StaffDAO staffDao;

    public StaffServiceImpl(StaffDAO staffDao) {
        this.staffDao = staffDao;
    }

    @Override
    public Staff findById(int id) throws ServiceException{
        return staffDao.findById(id);
    }

    @Override
    public List<Staff> getStaffInOrderAndLimit(int orderBy, int limit, int offset, int cruiseShipId) throws ServiceException {
        List<Staff> listStaff;
        try {
            listStaff = staffDao.getStaffInOrderAndLimit(orderBy, limit, offset, cruiseShipId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return listStaff;
    }

    @Override
    public List<Staff> getAllByCruiseId(int cruiseId) throws ServiceException{
        return staffDao.getAllByCruiseId(cruiseId);
    }

    @Override
    public void create(StaffDTO staffDTO) throws ServiceException{
        Staff staff = ConvertorUtil.convertDTOtoStaff(staffDTO);
        try {
            staffDao.create(staff);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(Staff staff) throws ServiceException{
        staffDao.delete(staff);
    }

    @Override
    public void update(StaffDTO staffDTO) throws ServiceException{
        Staff staff = ConvertorUtil.convertDTOtoStaff(staffDTO);
        try {
            staffDao.update(staff);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }
}
