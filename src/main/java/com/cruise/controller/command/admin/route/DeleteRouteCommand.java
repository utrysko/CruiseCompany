package com.cruise.controller.command.admin.route;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.exceptions.constants.ExceptionMessage;
import com.cruise.service.CruiseService;
import com.cruise.service.RouteService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteRouteCommand implements Command {
    private RouteService routeService;
    private CruiseService cruiseService;
    public DeleteRouteCommand(){
        this.routeService = AppContext.getInstance().getRouteService();
        this.cruiseService = AppContext.getInstance().getCruiseService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int idRoute = Integer.parseInt(req.getParameter("id"));
        try {
            routeService.delete(idRoute);
        } catch (ServiceException e) {
            req.setAttribute("error", ExceptionMessage.ERROR_ROUTE_DELETE);
        }
        return AllPath.ROUTES_COMMAND;
    }
}
