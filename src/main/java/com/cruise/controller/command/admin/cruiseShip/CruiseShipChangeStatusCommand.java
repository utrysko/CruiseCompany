package com.cruise.controller.command.admin.cruiseShip;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.CruiseShip;
import com.cruise.service.CruiseShipService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CruiseShipChangeStatusCommand implements Command {
    private CruiseShipService cruiseShipService;
    public CruiseShipChangeStatusCommand(){
        this.cruiseShipService = AppContext.getInstance().getCruiseShipService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.CRUISE_SHIPS_COMMAND;
        try {
            CruiseShip cruiseShip = cruiseShipService.findById(Integer.parseInt(req.getParameter("cruiseShipId")));
            cruiseShipService.changeStatus(cruiseShip, req.getParameter("status"));
        } catch (ServiceException e){
            req.getSession().setAttribute("error", e.getMessage());
        }
        return forward;
    }
}