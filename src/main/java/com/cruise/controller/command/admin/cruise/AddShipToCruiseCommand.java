package com.cruise.controller.command.admin.cruise;

import com.cruise.controller.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.CruiseShip;
import com.cruise.model.service.CruiseService;
import com.cruise.model.service.CruiseShipService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Class for adding ship to cruise.Accessible only by admin.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class AddShipToCruiseCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(AddShipToCruiseCommand.class);
    private final CruiseService cruiseService;
    private final CruiseShipService cruiseShipService;
    public AddShipToCruiseCommand(){
        cruiseService = AppContext.getInstance().getCruiseService();
        cruiseShipService = AppContext.getInstance().getCruiseShipService();
    }
    public AddShipToCruiseCommand(CruiseService cruiseService, CruiseShipService cruiseShipService){
        this.cruiseService = cruiseService;
        this.cruiseShipService = cruiseShipService;
    }

    /**
     * Called from main controller. Tries to add ship to cruise.
     *
     * @param req for choose ship from database
     * @return path to redirect from main controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.ADD_SHIP_TO_CRUISE_PAGE;
        List<CruiseShip> cruiseShips;
        if (req.getParameter("cruiseShipId") != null){
            return addShipToCruise(req);
        }
        try {
            int cruiseId = Integer.parseInt(req.getParameter("cruiseId"));
            Cruise cruise = cruiseService.findById(cruiseId);
            cruiseShips = cruiseShipService.getAllFreeCruiseShip(cruise);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        req.setAttribute("cruiseShips", cruiseShips);
        req.setAttribute("cruiseId", req.getParameter("cruiseId"));
        return forward;
    }

    /**
     * Method to get Cruise and Ship from request and add route to cruise.
     * @param req to get Cruise and Ship
     * @return path to redirect from main controller
     */
    private String addShipToCruise(HttpServletRequest req) {
        int cruiseShipId = Integer.parseInt(req.getParameter("cruiseShipId"));
        int cruiseId = Integer.parseInt(req.getParameter("cruiseId"));
        try {
            Cruise cruise = cruiseService.findById(cruiseId);
            CruiseShip cruiseShip = cruiseShipService.findById(cruiseShipId);
            cruiseService.addShipToCruise(cruiseShip, cruise);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.getSession().setAttribute("error", e.getMessage());
        }
        return AllPath.CRUISES_COMMAND;
    }
}
