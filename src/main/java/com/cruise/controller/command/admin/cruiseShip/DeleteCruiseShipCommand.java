package com.cruise.controller.command.admin.cruiseShip;

import com.cruise.controller.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.CruiseShip;
import com.cruise.model.service.CruiseShipService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class for deleting cruise ship. Accessible only by admin.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class DeleteCruiseShipCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(DeleteCruiseShipCommand.class);
    private final CruiseShipService cruiseShipService;
    public DeleteCruiseShipCommand(){
        this.cruiseShipService = AppContext.getInstance().getCruiseShipService();
    }

    /**
     * Called from main controller. Tries to delete cruise ship.
     *
     * @param req for get cruise ship
     * @return path to redirect from main controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.CRUISE_SHIPS_COMMAND;
        int cruiseShipId = Integer.parseInt(req.getParameter("id"));
        try {
            CruiseShip cruiseShip = cruiseShipService.findById(cruiseShipId);
            cruiseShipService.delete(cruiseShip);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
        }
        return forward;
    }
}
