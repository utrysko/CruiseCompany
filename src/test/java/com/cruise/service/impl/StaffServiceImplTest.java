package com.cruise.service.impl;

import com.cruise.model.dao.StaffDAO;
import com.cruise.dto.StaffDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Staff;
import com.cruise.model.service.StaffService;
import com.cruise.model.service.impl.StaffServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StaffServiceImplTest {
    static StaffDAO mockStaffDAO;
    static StaffService staffService;
    static Staff testStaff;
    static int staffId;

    @BeforeAll
    static public void globalSetUp() {
        staffId = 5;
        mockStaffDAO = mock(StaffDAO.class);
        staffService = new StaffServiceImpl(mockStaffDAO);
        testStaff = new Staff();
        testStaff.setFirstName("Mike");
        testStaff.setLastName("Tyson");
        testStaff.setPosition("Capitan");
        testStaff.setCruiseShipId(3);
    }

    @BeforeEach
    public void setUp() {
        reset(mockStaffDAO);
        when(mockStaffDAO.findById(staffId)).thenReturn(Optional.of(testStaff));
        when(mockStaffDAO.getStaffInOrderAndLimit(anyInt(), anyInt(), anyInt(), anyInt()))
                .thenReturn(List.of(testStaff));
        when(mockStaffDAO.countAll(anyInt())).thenReturn(1);
        when(mockStaffDAO.getAllByCruiseId(anyInt())).thenReturn(List.of(testStaff));
        doNothing().when(mockStaffDAO).create(any(Staff.class));
        doNothing().when(mockStaffDAO).delete(any(Staff.class));
    }

    @Test
    void findById() throws ServiceException {
        Staff staff = staffService.findById(staffId);

        verify(mockStaffDAO, times(1)).findById(anyInt());
        assertEquals(testStaff.getFirstName(), staff.getFirstName());
        assertThrows(ServiceException.class, () -> staffService.findById(0));
    }

    @Test
    void getStaffInOrderAndLimit() throws ServiceException{
        List<Staff> staff = staffService.getStaffInOrderAndLimit(1, 1, 1, testStaff.getCruiseShipId());

        verify(mockStaffDAO, times(1))
                .getStaffInOrderAndLimit(anyInt(), anyInt(), anyInt(), anyInt());
        assertEquals(testStaff.getLastName(), staff.get(0).getLastName());
    }

    @Test
    void countAll() throws ServiceException{
        int amount = staffService.countAll(testStaff.getCruiseShipId());

        verify(mockStaffDAO, times(1)).countAll(anyInt());
        assertEquals(1, amount);
    }

    @Test
    void getAllByCruiseId() throws ServiceException{
        List<Staff> staff = staffService.getAllByCruiseId(testStaff.getCruiseShipId());

        verify(mockStaffDAO, times(1)).getAllByCruiseId(anyInt());
        assertEquals(testStaff.getFirstName(), staff.get(0).getFirstName());
    }

    @Test
    void create() throws ServiceException{
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setFirstName("Andrew");
        staffDTO.setLastName("Smith");
        staffDTO.setPosition("Capitan");
        staffDTO.setCruiseShipId(3);
        staffService.create(staffDTO);

        verify(mockStaffDAO, times(1)).create(any(Staff.class));
    }

    @Test
    void delete() throws ServiceException{
        staffService.delete(testStaff);

        verify(mockStaffDAO, times(1)).delete(any(Staff.class));
    }

    @Test
    void update() throws ServiceException{
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setFirstName("Leon");
        staffDTO.setLastName("Born");
        staffDTO.setPosition("Capitan");
        staffDTO.setCruiseShipId(3);
        staffService.update(staffDTO);

        verify(mockStaffDAO, times(1)).update(any(Staff.class));
    }
}