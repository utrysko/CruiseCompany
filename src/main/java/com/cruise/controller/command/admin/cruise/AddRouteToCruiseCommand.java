package com.cruise.controller.command.admin.cruise;

import com.cruise.controller.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.utils.PaginationUtil;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.Route;
import com.cruise.model.service.CruiseService;
import com.cruise.model.service.RouteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Class for adding route to cruise.Accessible only by admin.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class AddRouteToCruiseCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(AddRouteToCruiseCommand.class);
    private final CruiseService cruiseService;
    private final RouteService routeService;
    public AddRouteToCruiseCommand(){
        cruiseService = AppContext.getInstance().getCruiseService();
        routeService = AppContext.getInstance().getRouteService();
    }

    public AddRouteToCruiseCommand(CruiseService cruiseService, RouteService routeService){
        this.cruiseService = cruiseService;
        this.routeService = routeService;
    }

    /**
     * Called from main controller. Tries to add route to cruise.
     *
     * @param req for choose route from database
     * @return path to redirect from main controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getParameter("routeId") != null){
            return addRouteToCruise(req);
        }
        String forward = AllPath.ADD_ROUTE_TO_CRUISE_PAGE;
        List<Route> routes;
        int orderBy = PaginationUtil.getSortBy(req);
        int limit = PaginationUtil.getLimit(req);
        int offset = PaginationUtil.getPage(req) * limit;
        int amount;
        int pages;
        int cruiseId = Integer.parseInt(req.getParameter("cruiseId"));
        try {
            amount = routeService.countAll();
            pages = PaginationUtil.getPages(amount, limit);
            routes = routeService.getRoutesInOrderAndLimit(orderBy, limit, offset);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        req.setAttribute("cruiseId", cruiseId);
        req.setAttribute("pages", pages);
        req.setAttribute("routes", routes);
        return forward;
    }

    /**
     * Method to get Cruise and Route from request and add route to cruise.
     *
     * @param req to get Cruise and Route
     * @return path to redirect from main controller
     */
    private String addRouteToCruise(HttpServletRequest req) {
        int routeId = Integer.parseInt(req.getParameter("routeId"));
        int cruiseId = Integer.parseInt(req.getParameter("cruiseId"));
        try {
            Route route = routeService.findById(routeId);
            Cruise cruise = cruiseService.findById(cruiseId);
            cruiseService.addRouteToCruise(route, cruise);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
        }
        return AllPath.CRUISES_COMMAND;
    }
}
