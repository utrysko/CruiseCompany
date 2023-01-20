package com.cruise.controller.command.admin.route;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.exceptions.constants.ExceptionMessage;
import com.cruise.service.RouteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteRouteCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(DeleteRouteCommand.class);
    private final RouteService routeService;
    public DeleteRouteCommand(){
        this.routeService = AppContext.getInstance().getRouteService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            routeService.delete(Integer.parseInt(req.getParameter("id")));
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.setAttribute("error", ExceptionMessage.ERROR_ROUTE_DELETE);
        }
        return AllPath.ROUTES_COMMAND;
    }
}
