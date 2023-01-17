package com.cruise.controller.command.admin.cruiseShip;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.dto.CruiseShipDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.service.CruiseShipService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;

public class AddCruiseShipCommand implements Command {
    private CruiseShipService cruiseShipService;
    public AddCruiseShipCommand(){
        this.cruiseShipService = AppContext.getInstance().getCruiseShipService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.CRUISE_SHIPS_COMMAND;
        CruiseShipDTO cruiseShipDTO = getCruiseShipDTO(req);
        try {
            cruiseShipService.create(cruiseShipDTO);
        } catch (ServiceException e){
            req.getSession().setAttribute("error", e.getMessage());
        }
        return forward;
    }
    private CruiseShipDTO getCruiseShipDTO(HttpServletRequest req){
        CruiseShipDTO cruiseShipDTO = new CruiseShipDTO();
        cruiseShipDTO.setName(req.getParameter("name"));
        cruiseShipDTO.setCapacity(Integer.parseInt(req.getParameter("capacity")));
        cruiseShipDTO.setFreeSpaces(Integer.parseInt(req.getParameter("freeSpaces")));
        cruiseShipDTO.setStatus(req.getParameter("status"));
        cruiseShipDTO.setAvailableFrom(Date.valueOf(LocalDate.now()));
        return cruiseShipDTO;
    }
}
