package com.cruise.utils;

import com.cruise.dto.*;
import com.cruise.model.entities.*;

/**
 * Util class for converting entity to DTO and vise versa
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class ConvertorUtil {

    private ConvertorUtil(){}

    /**
     * Convert UserDTO to User
     *
     * @param userDTO - instance of UserDTO
     * @return User
     */
    public static User convertUserDTOtoUser(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setLogin(userDTO.getLogin());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setRoleId(userDTO.getRoleId());
        user.setBalance(userDTO.getBalance());
        return user;
    }

    /**
     * Convert User to UserDTO
     *
     * @param user - instance of User
     * @return UserDTO
     */
    public static UserDTO convertUserToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoleId(user.getRoleId());
        userDTO.setBalance(user.getBalance());
        return userDTO;
    }

    /**
     * Convert CruiseShipDTO to CruiseShip
     *
     * @param cruiseShipDTO - instance of CruiseShipDTO
     * @return CruiseShip
     */
    public static CruiseShip convertDTOtoCruiseShip(CruiseShipDTO cruiseShipDTO){
        CruiseShip cruiseShip = new CruiseShip();
        cruiseShip.setId(cruiseShipDTO.getId());
        cruiseShip.setName(cruiseShipDTO.getName());
        cruiseShip.setCapacity(cruiseShipDTO.getCapacity());
        cruiseShip.setStatus(cruiseShipDTO.getStatus());
        cruiseShip.setAvailableFrom(cruiseShipDTO.getAvailableFrom());
        return cruiseShip;
    }

    /**
     * Convert CruiseShip to CruiseShipDTO
     *
     * @param cruiseShip - instance of CruiseShip
     * @return CruiseShipDTO
     */
    public static CruiseShipDTO convertCruiseShipToDTO(CruiseShip cruiseShip){
        CruiseShipDTO cruiseShipDTO = new CruiseShipDTO();
        cruiseShipDTO.setId(cruiseShip.getId());
        cruiseShipDTO.setName(cruiseShip.getName());
        cruiseShipDTO.setCapacity(cruiseShip.getCapacity());
        cruiseShipDTO.setStatus(cruiseShip.getStatus());
        cruiseShipDTO.setStaff(cruiseShip.getStaff());
        return cruiseShipDTO;
    }

    /**
     * Convert RouteDTO to Route
     *
     * @param routeDTO - instance of RouteDTO
     * @return Route
     */
    public static Route convertDTOtoRoute(RouteDTO routeDTO){
        Route route = new Route();
        route.setId(routeDTO.getId());
        route.setNumberOfPorts(routeDTO.getNumberOfPorts());
        route.setStartPort(routeDTO.getStartPort());
        route.setMiddlePorts(routeDTO.getMiddlePorts());
        route.setEndPort(routeDTO.getEndPort());
        return route;
    }

    /**
     * Convert Route to RouteDTO
     *
     * @param route - instance of Route
     * @return RouteDTO
     */
    public static RouteDTO convertRouteToDTO(Route route){
        RouteDTO routeDTO = new RouteDTO();
        routeDTO.setId(route.getId());
        routeDTO.setNumberOfPorts(route.getNumberOfPorts());
        routeDTO.setStartPort(route.getStartPort());
        routeDTO.setMiddlePorts(route.getMiddlePorts());
        routeDTO.setEndPort(route.getEndPort());
        return routeDTO;
    }

    /**
     * Convert CruiseDTO to Cruise
     *
     * @param cruiseDTO - instance of CruiseDTO
     * @return Cruise
     */
    public static Cruise convertDTOtoCruise(CruiseDTO cruiseDTO){
        Cruise cruise = new Cruise();
        cruise.setId(cruiseDTO.getId());
        cruise.setStart(cruiseDTO.getStart());
        cruise.setEnd(cruiseDTO.getEnd());
        cruise.setStatus(cruiseDTO.getStatus());
        cruise.setFreeSpaces(cruiseDTO.getFreeSpaces());
        cruise.setTicketPrice(cruiseDTO.getTicketPrice());
        cruise.setCruiseShip(cruiseDTO.getCruiseShip());
        cruise.setRoute(cruiseDTO.getRoute());
        return cruise;
    }

    /**
     * Convert Cruise to CruiseDTO
     *
     * @param cruise - instance of Cruise
     * @return CruiseDTO
     */
    public static CruiseDTO convertCruiseToDTO(Cruise cruise){
        CruiseDTO cruiseDTO = new CruiseDTO();
        cruiseDTO.setId(cruise.getId());
        cruiseDTO.setStart(cruise.getStart());
        cruiseDTO.setEnd(cruise.getEnd());
        cruiseDTO.setStatus(cruise.getStatus());
        cruiseDTO.setTicketPrice(cruise.getTicketPrice());
        cruiseDTO.setCruiseShip(cruise.getCruiseShip());
        cruiseDTO.setRoute(cruise.getRoute());
        return cruiseDTO;
    }

    /**
     * Convert StaffDTO to Staff
     *
     * @param staffDTO - instance of StaffDTO
     * @return Staff
     */
    public static Staff convertDTOtoStaff(StaffDTO staffDTO){
        Staff staff = new Staff();
        staff.setFirstName(staffDTO.getFirstName());
        staff.setLastName(staffDTO.getLastName());
        staff.setPosition(staffDTO.getPosition());
        staff.setCruiseShipId(staffDTO.getCruiseShipId());
        return staff;
    }
}
