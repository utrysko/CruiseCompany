package com.cruise.controller.command.admin.cruise;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Cruise;
import com.cruise.model.CruiseShip;
import com.cruise.service.CruiseService;
import com.cruise.service.CruiseShipService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;

public class DeleteCruiseCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(DeleteCruiseCommand.class);
    private CruiseService cruiseService;
    private CruiseShipService cruiseShipService;
    public DeleteCruiseCommand(){
        cruiseService = AppContext.getInstance().getCruiseService();
        cruiseShipService = AppContext.getInstance().getCruiseShipService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.CRUISES_COMMAND;
        int cruiseId = Integer.parseInt(req.getParameter("id"));
        try {
            Cruise cruise = cruiseService.findById(cruiseId);
            cruiseService.delete(cruise);
            cruiseShipService.changeAvailableDate(cruise.getCruiseShip(), Date.valueOf(LocalDate.now()));
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
        }
        return forward;
    }
}
