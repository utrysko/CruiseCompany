package com.cruise.controller.command.admin.route;

import com.cruise.controller.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.utils.PaginationUtil;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Route;
import com.cruise.model.service.RouteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for getting routes. Accessible only by admin.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class RoutesCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(RoutesCommand.class);

    private final RouteService routeService;
    public RoutesCommand(){
        this.routeService = AppContext.getInstance().getRouteService();
    }

    /**
     * Called from main controller. Tries to get routes in some order.
     *
     * @param req for get parameters to getting routes
     * @return path to forward from main controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.ROUTES_PAGE;
        String routeId = req.getParameter("findRouteId");
        List<Route> routes = new ArrayList<>();
        if (routeId != null){
            return getByID(req, forward, routeId, routes);
        }
        int orderBy = PaginationUtil.getSortBy(req);
        int limit = PaginationUtil.getLimit(req);
        int offset = PaginationUtil.getPage(req) * limit;
        int amount;
        int pages;
        try {
            amount = routeService.countAll();
            pages = PaginationUtil.getPages(amount, limit);
            routes = routeService.getRoutesInOrderAndLimit(orderBy, limit, offset);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        req.setAttribute("pages", pages);
        req.setAttribute("routes", routes);
        return forward;
    }

    /**
     * Method to get Route by id.
     * @param req to get Route Id
     * @return path to forward from main controller
     */
    private String getByID(HttpServletRequest req, String forward, String routeId, List<Route> routes) {
        try {
            routes.add(routeService.findById(Integer.parseInt(routeId)));
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
        }
        req.setAttribute("routes", routes);
        return forward;
    }
}
