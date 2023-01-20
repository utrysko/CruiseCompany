package com.cruise.controller.command.admin.cruiseShip;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.CruiseShip;
import com.cruise.service.CruiseShipService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCruiseShipCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(DeleteCruiseShipCommand.class);
    private final CruiseShipService cruiseShipService;
    public DeleteCruiseShipCommand(){
        this.cruiseShipService = AppContext.getInstance().getCruiseShipService();
    }
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
