package com.cruise.controller.command.admin.cruiseShip;

import com.cruise.controller.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.dto.CruiseShipDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.CruiseShip;
import com.cruise.model.service.CruiseShipService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class for edit cruise ship. Accessible only by admin.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class EditCruiseShipCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(EditCruiseShipCommand.class);
    private final CruiseShipService cruiseShipService;
    public EditCruiseShipCommand(){
        this.cruiseShipService = AppContext.getInstance().getCruiseShipService();
    }

    /**
     * Called from main controller. Tries to change cruise ship.
     *
     * @param req for get cruiseShipDTO
     * @return path to redirect from main controller
     */
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

    /**
     * Method to get CruiseShipDTO from request
     * @param req to get cruiseShipDTO
     * @return CruiseShipDTO
     */
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
        return cruiseShipDTO;
    }
}
