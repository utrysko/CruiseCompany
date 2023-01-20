package com.cruise.controller.command.admin.cruiseShip;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.dto.CruiseShipDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.CruiseShip;
import com.cruise.service.CruiseShipService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditCruiseShipCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(EditCruiseShipCommand.class);
    private final CruiseShipService cruiseShipService;
    public EditCruiseShipCommand(){
        this.cruiseShipService = AppContext.getInstance().getCruiseShipService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.CRUISE_SHIPS_COMMAND;
        CruiseShipDTO cruiseShipDTO;
        try {
            cruiseShipDTO = getCruiseShipDTO(req);
            cruiseShipService.update(cruiseShipDTO);
        } catch (ServiceException e){
            LOG.error(e.getMessage());
            req.getSession().setAttribute("error", e.getMessage());
        }
        return forward;
    }

    private CruiseShipDTO getCruiseShipDTO(HttpServletRequest req) throws ServiceException{
        String cruiseShipId = req.getParameter("cruiseShipId");
        CruiseShip cruiseShip;
        try {
            cruiseShip = cruiseShipService.findById(Integer.parseInt(cruiseShipId));
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage());
        }
        String name = req.getParameter("name");
        String capacity = req.getParameter("capacity");
        String freeSpaces = req.getParameter("freeSpaces");

        CruiseShipDTO cruiseShipDTO = new CruiseShipDTO();
        cruiseShipDTO.setId(Integer.parseInt(cruiseShipId));
        if (name != null && !name.isEmpty()){
            cruiseShipDTO.setName(name);
        } else {
            cruiseShipDTO.setName(cruiseShip.getName());
        }
        if (capacity != null && !capacity.isEmpty()){
            cruiseShipDTO.setCapacity(Integer.parseInt(capacity));
        }else {
            cruiseShipDTO.setCapacity(cruiseShip.getCapacity());
        }
        if (freeSpaces != null && !freeSpaces.isEmpty()){
            cruiseShipDTO.setFreeSpaces(Integer.parseInt(freeSpaces));
        }else {
            cruiseShipDTO.setFreeSpaces(cruiseShip.getFreeSpaces());
        }
        return cruiseShipDTO;
    }
}
