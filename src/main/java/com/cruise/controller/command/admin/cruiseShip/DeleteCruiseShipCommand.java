package com.cruise.controller.command.admin.cruiseShip;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.CruiseShip;
import com.cruise.service.CruiseShipService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCruiseShipCommand implements Command {
    private CruiseShipService cruiseShipService;
    public DeleteCruiseShipCommand(){
        this.cruiseShipService = AppContext.getInstance().getCruiseShipService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.CRUISE_SHIPS_COMMAND;
        int cruiseShipId = Integer.parseInt(req.getParameter("id"));
        try {
            CruiseShip cruiseShip = cruiseShipService.findById(cruiseShipId);
            cruiseShipService.delete(cruiseShip);
        } catch (ServiceException e) {
            req.setAttribute("error", "cruise ship is used");
        }
        return forward;
    }
}
