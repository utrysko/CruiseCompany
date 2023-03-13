package com.cruise.controller.command.admin.cruiseShip;

import com.cruise.controller.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.dto.CruiseShipDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.service.CruiseShipService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Class for adding new Cruise Ship. Accessible only by admin.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class AddCruiseShipCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(AddCruiseShipCommand.class);
    private final CruiseShipService cruiseShipService;
    public AddCruiseShipCommand(){
        this.cruiseShipService = AppContext.getInstance().getCruiseShipService();
    }
    public AddCruiseShipCommand(CruiseShipService cruiseShipService){
        this.cruiseShipService = cruiseShipService;
    }

    /**
     * Called from main controller. Tries to add new cruise ship.
     *
     * @param req to get cruise ship instance
     * @return path to redirect from main controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.CRUISE_SHIPS_COMMAND;
        CruiseShipDTO cruiseShipDTO = getCruiseShipDTO(req);
        try {
            cruiseShipService.create(cruiseShipDTO);
        } catch (ServiceException e){
            LOG.error(e.getMessage());
            req.getSession().setAttribute("error", e.getMessage());
        }
        return forward;
    }

    /**
     * Method to get CruiseShipDTO from request
     * @param req to get cruiseShipDTO
     * @return CruiseShipDTO
     */
    private CruiseShipDTO getCruiseShipDTO(HttpServletRequest req){
        CruiseShipDTO cruiseShipDTO = new CruiseShipDTO();
        cruiseShipDTO.setName(req.getParameter("name"));
        cruiseShipDTO.setCapacity(Integer.parseInt(req.getParameter("capacity")));
        cruiseShipDTO.setStatus(req.getParameter("status"));
        cruiseShipDTO.setAvailableFrom(Date.valueOf(LocalDate.now()));
        return cruiseShipDTO;
    }
}
