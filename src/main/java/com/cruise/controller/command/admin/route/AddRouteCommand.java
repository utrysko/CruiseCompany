package com.cruise.controller.command.admin.route;

import com.cruise.controller.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.dto.RouteDTO;
import com.cruise.model.service.RouteService;
import com.cruise.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for adding new Route. Accessible only by admin.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class AddRouteCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(AddRouteCommand.class);
    private final RouteService routeService;
    public AddRouteCommand(){
        this.routeService = AppContext.getInstance().getRouteService();
    }

    /**
     * Called from main controller. Tries to add new route.
     *
     * @param req to get RouteDTO instance
     * @return path to redirect from main controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.ADD_ROUTE_PAGE;
        RouteDTO routeDTO;
        HttpSession session = req.getSession();
        int numberOfPorts = Integer.parseInt(req.getParameter("numberOfPorts"));
        if (numberOfPorts < 2){
            session.setAttribute("error", "number of ports cannot be less then 2");
            return AllPath.ROUTES_COMMAND;
        }
        if (req.getParameter("startPort") == null){
            req.setAttribute("numberOfPorts", numberOfPorts);
            return forward;
        }
        forward = AllPath.ROUTES_COMMAND;
        routeDTO = getRouteDTO(req);
        try {
            routeService.create(routeDTO);
        } catch (ServiceException e){
            LOG.error(e.getMessage());
            session.setAttribute("error", e.getMessage());
        }
        return forward;
    }

    /**
     * Method to get RouteDTO from request
     * @param req to get routeDTO
     * @return RouteDTO
     */
    private RouteDTO getRouteDTO(HttpServletRequest req){
        RouteDTO routeDTO = new RouteDTO();
        List<String> middlePorts = new ArrayList<>();
        int numberOfPorts = Integer.parseInt(req.getParameter("numberOfPorts"));
        for (int i = 2; i < numberOfPorts; i++){
            middlePorts.add(req.getParameter("middlePort" + i));
        }
        routeDTO.setNumberOfPorts(numberOfPorts);
        routeDTO.setStartPort(req.getParameter("startPort"));
        routeDTO.setMiddlePorts(middlePorts);
        routeDTO.setEndPort(req.getParameter("endPort"));
        return routeDTO;
    }
}
