package com.cruise.controller.command.admin.cruiseShip;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.PaginationUtil;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.CruiseShip;
import com.cruise.service.CruiseShipService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class CruiseShipsCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(CruiseShipsCommand.class);
    private final CruiseShipService cruiseShipService;
    public CruiseShipsCommand(){
        cruiseShipService = AppContext.getInstance().getCruiseShipService();
    }
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