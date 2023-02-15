package com.cruise.service.impl;

import com.cruise.model.dao.CruiseShipDAO;
import com.cruise.dto.CruiseShipDTO;
import com.cruise.exceptions.InvalidFormatException;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.CruiseShip;
import com.cruise.model.service.CruiseShipService;
import com.cruise.model.service.impl.CruiseShipServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class CruiseShipServiceImplTest {

    static CruiseShipDAO mockCruiseShipDAO;
    static CruiseShipService cruiseShipService;
    static CruiseShip testCruiseShip;
    static int cruiseShipId;

    @BeforeAll
    static public void globalSetUp() {
        cruiseShipId = 16;
        testCruiseShip = new CruiseShip();
        testCruiseShip.setName("Ember");
        testCruiseShip.setCapacity(100);
        testCruiseShip.setStatus("Available");
        testCruiseShip.setAvailableFrom(Date.valueOf(LocalDate.now()));
        mockCruiseShipDAO = mock(CruiseShipDAO.class);
        cruiseShipService = new CruiseShipServiceImpl(mockCruiseShipDAO);
    }

    @BeforeEach
    public void setUp() {
        reset(mockCruiseShipDAO);
        when(mockCruiseShipDAO.findById(cruiseShipId)).thenReturn(Optional.of(testCruiseShip));
        when(mockCruiseShipDAO.findByName(testCruiseShip.getName())).thenReturn(Optional.of(testCruiseShip));
        doNothing().when(mockCruiseShipDAO).create(any(CruiseShip.class));
        when(mockCruiseShipDAO.getAllCruiseShip()).thenReturn(List.of(testCruiseShip));
        when(mockCruiseShipDAO.countAll()).thenReturn(1);
        when(mockCruiseShipDAO.getFreeCruiseShip(any(Cruise.class))).thenReturn(new ArrayList<>());
        when(mockCruiseShipDAO.getCruiseShipsInOrderAndLimit(anyInt(), anyInt(), anyInt())).thenReturn(List.of(testCruiseShip));
        doNothing().when(mockCruiseShipDAO).update(any(CruiseShip.class));
        doNothing().when(mockCruiseShipDAO).delete(any(CruiseShip.class));
        doNothing().when(mockCruiseShipDAO).changeStatus(any(CruiseShip.class), anyString());
        doNothing().when(mockCruiseShipDAO).changeAvailableDate(any(CruiseShip.class), any(Date.class));
    }

    @Test
    void findById() throws ServiceException {
        CruiseShip cruiseShip = cruiseShipService.findById(cruiseShipId);

        verify(mockCruiseShipDAO, times(1)).findById(anyInt());
        assertEquals(testCruiseShip.getName(), cruiseShip.getName());
        assertEquals(testCruiseShip.getStatus(), cruiseShip.getStatus());
    }

    @Test
    void findByName() throws ServiceException{
        CruiseShip cruiseShip = cruiseShipService.findByName("Ember");

        verify(mockCruiseShipDAO, times(1)).findByName(anyString());
        assertEquals(testCruiseShip.getName(), cruiseShip.getName());
        assertEquals(testCruiseShip.getStatus(), cruiseShip.getStatus());
    }

    @Test
    void create() throws ServiceException{
        CruiseShipDTO cruiseShipDTO = new CruiseShipDTO();
        cruiseShipDTO.setName("Kronos");
        cruiseShipDTO.setCapacity(50);
        cruiseShipDTO.setStatus("Available");
        cruiseShipDTO.setAvailableFrom(Date.valueOf(LocalDate.now()));

        cruiseShipService.create(cruiseShipDTO);
        verify(mockCruiseShipDAO, times(1)).findByName(anyString());
        verify(mockCruiseShipDAO, times(1)).create(any(CruiseShip.class));
    }

    @Test
    void getAllCruiseShip() throws ServiceException{
        List<CruiseShip> cruiseShips = cruiseShipService.getAllCruiseShip();

        verify(mockCruiseShipDAO, times(1)).getAllCruiseShip();
        assertEquals(testCruiseShip.getName(), cruiseShips.get(0).getName());
    }

    @Test
    void countAll() throws ServiceException{
        int amount = cruiseShipService.countAll();

        verify(mockCruiseShipDAO, times(1)).countAll();
        assertEquals(1, amount);
    }

    @Test
    void getAllFreeCruiseShip() throws ServiceException{
        List<CruiseShip> cruiseShips = cruiseShipService.getAllFreeCruiseShip(new Cruise());

        verify(mockCruiseShipDAO, times(1)).getFreeCruiseShip(any(Cruise.class));
        assertEquals(0, cruiseShips.size());
    }

    @Test
    void getCruiseShipsInOrderAndLimit() throws ServiceException{
        List<CruiseShip> cruiseShips = cruiseShipService.getCruiseShipsInOrderAndLimit(1, 1, 1);

        verify(mockCruiseShipDAO, times(1)).getCruiseShipsInOrderAndLimit(anyInt(), anyInt(), anyInt());
        assertEquals(1, cruiseShips.size());
        assertEquals(testCruiseShip.getName(), cruiseShips.get(0).getName());
    }

    @Test
    void update() throws ServiceException{
        CruiseShipDTO cruiseShipDTO = new CruiseShipDTO();
        cruiseShipDTO.setId(19);
        cruiseShipDTO.setName("Kronos");
        cruiseShipDTO.setCapacity(50);
        cruiseShipDTO.setStatus("Available");
        cruiseShipDTO.setAvailableFrom(Date.valueOf(LocalDate.now()));

        cruiseShipService.update(cruiseShipDTO);
        verify(mockCruiseShipDAO, times(1)).findByName(anyString());
        verify(mockCruiseShipDAO, times(1)).update(any(CruiseShip.class));

        cruiseShipDTO.setCapacity(-20);
        assertThrows(InvalidFormatException.class, () -> cruiseShipService.create(cruiseShipDTO));
    }

    @Test
    void delete() throws ServiceException{
        cruiseShipService.delete(testCruiseShip);
        verify(mockCruiseShipDAO, times(1)).delete(any(CruiseShip.class));

        testCruiseShip.setStatus("Used");
        assertThrows(ServiceException.class, () -> cruiseShipService.delete(testCruiseShip));
    }

    @Test
    void changeStatus() throws ServiceException{
        cruiseShipService.changeStatus(testCruiseShip, "new status");

        verify(mockCruiseShipDAO, times(1)).changeStatus(any(CruiseShip.class), anyString());
    }

    @Test
    void changeAvailableDate() throws ServiceException{
        cruiseShipService.changeAvailableDate(testCruiseShip, Date.valueOf(LocalDate.now().plusDays(5)));

        verify(mockCruiseShipDAO, times(1)).changeAvailableDate(any(CruiseShip.class), any(Date.class));
    }
}