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
 * Class for changing cruise ship status. Accessible only by admin.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class CruiseShipChangeStatusCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(CruiseShipChangeStatusCommand.class);
    private final CruiseShipService cruiseShipService;
    public CruiseShipChangeStatusCommand(){
        this.cruiseShipService = AppContext.getInstance().getCruiseShipService();
    }

    /**
     * Called from main controller. Tries to change cruise ship status.
     *
     * @param req for get ship and new status
     * @return path to redirect from main controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.CRUISE_SHIPS_COMMAND;
        try {
            CruiseShip cruiseShip = cruiseShipService.findById(Integer.parseInt(req.getParameter("cruiseShipId")));
            cruiseShipService.changeStatus(cruiseShip, req.getParameter("status"));
        } catch (ServiceException e){
            LOG.error(e.getMessage());
            req.getSession().setAttribute("error", e.getMessage());
        }
        return forward;
    }
}