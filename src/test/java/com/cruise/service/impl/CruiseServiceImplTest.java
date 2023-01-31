package com.cruise.service.impl;

import com.cruise.model.dao.CruiseDAO;
import com.cruise.model.dao.CruiseShipDAO;
import com.cruise.dto.CruiseDTO;
import com.cruise.exceptions.InvalidFormatException;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.CruiseShip;
import com.cruise.model.entities.Route;
import com.cruise.model.service.CruiseService;
import com.cruise.model.service.impl.CruiseServiceImpl;
import com.cruise.utils.ConvertorUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CruiseServiceImplTest {

    static CruiseDAO mockCruiseDao;
    static CruiseShipDAO mockCruiseShipDAO;
    static CruiseService cruiseService;
    static CruiseDTO testCruiseDTO;
    static Cruise testCruise;
    static int cruiseId;
    static CruiseShip testCruiseShip;
    static Route testRoute;

    @BeforeAll
    static public void globalSetUp() {
        cruiseId = 13;
        testCruiseDTO = new CruiseDTO();
        testCruiseDTO.setStart(Date.valueOf(LocalDate.now().plusDays(1)));
        testCruiseDTO.setEnd(Date.valueOf(LocalDate.now().plusDays(6)));
        testCruiseDTO.setTicketPrice(45.0);
        testCruiseDTO.setFreeSpaces(50);
        testCruiseDTO.setStatus("Available");
        testCruise = ConvertorUtil.convertDTOtoCruise(testCruiseDTO);
        testRoute = new Route();
        testRoute.setId(6);
        testCruiseShip = new CruiseShip();
        testCruiseShip.setId(9);
        testCruiseShip.setCapacity(50);
        testCruise.setCruiseShip(testCruiseShip);
        mockCruiseDao = mock(CruiseDAO.class);
        mockCruiseShipDAO = mock(CruiseShipDAO.class);
        cruiseService = new CruiseServiceImpl(mockCruiseDao, mockCruiseShipDAO);
    }

    @BeforeEach
    public void setUp() {
        reset(mockCruiseDao);
        reset(mockCruiseShipDAO);
        when(mockCruiseDao.findById(cruiseId)).thenReturn(testCruise);
        doNothing().when(mockCruiseDao).create(any(Cruise.class));
        when(mockCruiseDao.getAllCruise()).thenReturn(List.of(testCruise));
        when(mockCruiseShipDAO.getFreeCruiseShip(any(Cruise.class))).thenReturn(List.of(testCruiseShip));
        when(mockCruiseDao.getCruisesInOrderAndLimit(anyInt(), anyInt(), anyInt())).thenReturn(List.of(testCruise));
        doNothing().when(mockCruiseDao).update(any(Cruise.class));
        doNothing().when(mockCruiseDao).delete(any(Cruise.class));
        doNothing().when(mockCruiseDao).changeStatus(any(Cruise.class), anyString());
        doNothing().when(mockCruiseDao).addShipToCruise(any(CruiseShip.class), any(Cruise.class));
        doNothing().when(mockCruiseDao).addRouteToCruise(any(Route.class), any(Cruise.class));
        doNothing().when(mockCruiseDao).changeFreeSpaces(any(Cruise.class), anyInt());
        doNothing().when(mockCruiseShipDAO).changeAvailableDate(any(CruiseShip.class), any(Date.class));
        when(mockCruiseDao.countAll()).thenReturn(1);
    }

    @Test
    void findById() throws ServiceException {
        Cruise cruise = cruiseService.findById(cruiseId);

        assertEquals(testCruise.getStart(), cruise.getStart());
        assertEquals(testCruise.getTicketPrice(), cruise.getTicketPrice());
        assertThrows(InvalidFormatException.class, () -> cruiseService.findById(-1));
    }

    @Test
    void create() throws ServiceException{
        cruiseService.create(testCruiseDTO);
        CruiseDTO cruiseDTO = new CruiseDTO();
        cruiseDTO.setStart(Date.valueOf(LocalDate.now().minusDays(2)));
        cruiseDTO.setEnd(Date.valueOf(LocalDate.now().minusDays(3)));
        cruiseDTO.setTicketPrice(0);
        cruiseDTO.setFreeSpaces(50);

        verify(mockCruiseDao, times(1)).create(any(Cruise.class));

        assertThrows(InvalidFormatException.class, () -> cruiseService.create(cruiseDTO));
    }

    @Test
    void getAllCruise() throws ServiceException{
        List<Cruise> cruises = cruiseService.getAllCruise();

        assertEquals(1, cruises.size());
        assertEquals(testCruise.getStart(), cruises.get(0).getStart());
    }

    @Test
    void countAll() throws ServiceException{
        int amount = cruiseService.countAll();

        verify(mockCruiseDao, times(1)).countAll();
        assertEquals(1, amount);
    }

    @Test
    void getAllFreeShip() throws ServiceException{
        List<CruiseShip> cruiseShips = cruiseService.getAllFreeShip(testCruise);

        assertEquals(1, cruiseShips.size());
        assertEquals(testCruiseShip.getName(), cruiseShips.get(0).getName());
    }

    @Test
    void getCruisesInOrderAndLimit() throws ServiceException{
        List<Cruise> cruises = cruiseService.getCruisesInOrderAndLimit(1, 1, 1);

        assertEquals(1, cruises.size());
        assertEquals(testCruise.getStart(), cruises.get(0).getStart());
    }

    @Test
    void update() throws ServiceException{
        cruiseService.update(testCruiseDTO);
        CruiseDTO cruiseDTO = new CruiseDTO();
        cruiseDTO.setStart(Date.valueOf(LocalDate.now().minusDays(2)));
        cruiseDTO.setEnd(Date.valueOf(LocalDate.now().minusDays(3)));
        cruiseDTO.setTicketPrice(0);
        cruiseDTO.setFreeSpaces(50);

        verify(mockCruiseDao, times(1)).update(any(Cruise.class));

        assertThrows(InvalidFormatException.class, () -> cruiseService.update(cruiseDTO));
    }

    @Test
    void delete() throws ServiceException{
        cruiseService.delete(testCruise);

        verify(mockCruiseDao, times(1)).delete(any(Cruise.class));
        verify(mockCruiseShipDAO, times(1)).changeAvailableDate(any(CruiseShip.class), any(Date.class));
    }

    @Test
    void changeStatus() throws ServiceException{
        cruiseService.changeStatus(testCruise, "Status");

        verify(mockCruiseDao, times(1)).changeStatus(any(Cruise.class), anyString());
    }

    @Test
    void addShipToCruise() throws ServiceException{
        cruiseService.addShipToCruise(testCruiseShip, testCruise);

        verify(mockCruiseDao, times(1)).addShipToCruise(any(CruiseShip.class), any(Cruise.class));
        verify(mockCruiseShipDAO, times(2)).changeAvailableDate(any(CruiseShip.class), any(Date.class));
    }

    @Test
    void addRouteToCruise() throws ServiceException{
        cruiseService.addShipToCruise(testCruiseShip, testCruise);

        verify(mockCruiseDao, times(1)).addShipToCruise(any(CruiseShip.class), any(Cruise.class));
    }

    @Test
    void changeFreeSpaces() throws ServiceException{
        testCruise.setCruiseShip(testCruiseShip);
        cruiseService.changeFreeSpaces(testCruise, 25);

        verify(mockCruiseDao, times(1)).changeFreeSpaces(any(Cruise.class), anyInt());
    }
}