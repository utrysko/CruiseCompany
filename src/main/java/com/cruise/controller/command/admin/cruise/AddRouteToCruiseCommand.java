package com.cruise.controller.command.admin.cruise;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.PaginationUtil;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Cruise;
import com.cruise.model.Route;
import com.cruise.service.CruiseService;
import com.cruise.service.RouteService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AddRouteToCruiseCommand implements Command {
    private CruiseService cruiseService;
    private RouteService routeService;
    public AddRouteToCruiseCommand(){
        cruiseService = AppContext.getInstance().getCruiseService();
        routeService = AppContext.getInstance().getRouteService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.ADD_ROUTE_TO_CRUISE_PAGE;
        List<Route> routes;
        if (req.getParameter("routeId") != null){
            int routeId = Integer.parseInt(req.getParameter("routeId"));
            int cruiseId = Integer.parseInt(req.getParameter("cruiseId"));
            try {
                Route route = routeService.findById(routeId);
                Cruise cruise = cruiseService.findById(cruiseId);
                cruiseService.addRouteToCruise(route, cruise);
            } catch (ServiceException e) {
                req.setAttribute("error", e.getMessage());
            }
            return AllPath.CRUISES_COMMAND;
        }

        int orderBy = PaginationUtil.getSortBy(req);
        int limit = PaginationUtil.getLimit(req);
        int offset = PaginationUtil.getPage(req) * limit;
        int amount;
        int pages;
        int cruiseId = Integer.parseInt(req.getParameter("cruiseId"));
        try {
            amount = routeService.getAllRoutes().size();
            pages = PaginationUtil.getPages(amount, limit);
            routes = routeService.getRoutesInOrderAndLimit(orderBy, limit, offset);
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        req.setAttribute("cruiseId", cruiseId);
        req.setAttribute("pages", pages);
        req.setAttribute("routes", routes);
        return forward;
    }
}
