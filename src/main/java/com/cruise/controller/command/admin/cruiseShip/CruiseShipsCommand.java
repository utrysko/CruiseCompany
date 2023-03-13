package com.cruise.controller.command.admin.cruiseShip;

import com.cruise.controller.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.utils.PaginationUtil;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.CruiseShip;
import com.cruise.model.service.CruiseShipService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for getting cruise ships. Accessible only by admin.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class CruiseShipsCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(CruiseShipsCommand.class);
    private final CruiseShipService cruiseShipService;
    public CruiseShipsCommand(){
        cruiseShipService = AppContext.getInstance().getCruiseShipService();
    }
    public CruiseShipsCommand(CruiseShipService cruiseShipService){
        this.cruiseShipService = cruiseShipService;
    }

    /**
     * Called from main controller. Tries to get cruise ships in some order.
     *
     * @param req for get parameters to getting cruise ships
     * @return path to forward from main controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.CRUISE_SHIPS_PAGE;
        String cruiseShipId = req.getParameter("findCruiseShipId");
        List<CruiseShip> cruiseShips = new ArrayList<>();
        if (cruiseShipId != null){
            return getByID(req, forward, cruiseShipId, cruiseShips);
        }
        int orderBy = PaginationUtil.getSortBy(req);
        int limit = PaginationUtil.getLimit(req);
        int offset = PaginationUtil.getPage(req) * limit;
        int amount;
        int pages;
        try {
            amount = cruiseShipService.countAll();
            pages = PaginationUtil.getPages(amount, limit);
            cruiseShips = cruiseShipService.getCruiseShipsInOrderAndLimit(orderBy, limit, offset);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        req.setAttribute("pages", pages);
        req.setAttribute("cruiseShips", cruiseShips);
        return forward;
    }

    /**
     * Method to get CruiseShip by id.
     * @param req to get CruiseShip Id
     * @return path to forward from main controller
     */
    private String getByID(HttpServletRequest req, String forward, String cruiseShipId, List<CruiseShip> cruiseShips) {
        try {
            cruiseShips.add(cruiseShipService.findById(Integer.parseInt(cruiseShipId)));
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
        }
        req.setAttribute("cruiseShips", cruiseShips);
        return forward;
    }
}