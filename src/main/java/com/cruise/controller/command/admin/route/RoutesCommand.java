package com.cruise.controller.command.admin.route;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.PaginationUtil;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Route;
import com.cruise.service.RouteService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class RoutesCommand implements Command {
    private RouteService routeService;
    public RoutesCommand(){
        this.routeService = AppContext.getInstance().getRouteService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.ROUTES_PAGE;
        String routeId = req.getParameter("findRouteId");
        List<Route> routes = new ArrayList<>();
        if (routeId != null){
            try {
                routes.add(routeService.findById(Integer.parseInt(routeId)));
            } catch (ServiceException e) {
                req.setAttribute("error", e.getMessage());
            }
            req.setAttribute("routes", routes);
            return forward;
        }
        int orderBy = PaginationUtil.getSortBy(req);
        int limit = PaginationUtil.getLimit(req);
        int offset = PaginationUtil.getPage(req) * limit;
        int amount;
        try {
            amount = routeService.getAllRoutes().size();
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        int pages = PaginationUtil.getPages(amount, limit);
        try {
            routes = routeService.getRoutesInOrderAndLimit(orderBy, limit, offset);
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        req.setAttribute("pages", pages);
        req.setAttribute("routes", routes);
        return forward;
    }
}
